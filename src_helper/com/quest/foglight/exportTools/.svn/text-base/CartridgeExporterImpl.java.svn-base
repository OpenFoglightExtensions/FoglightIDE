package com.quest.foglight.exportTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.commons.io.FileUtils;
import org.jboss.mx.util.MBeanServerLocator;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;

import com.quest.foglight.exportTools.interfaces.CartridgeExporter;
import com.quest.nitro.model.topology.DataObject;
import com.quest.nitro.model.topology.TopologyObject;
import com.quest.nitro.service.cartridge.api.CartFile;
import com.quest.nitro.service.cartridge.api.Cartridge;
import com.quest.nitro.service.cartridge.api.CartridgeBuilder;
import com.quest.nitro.service.cartridge.api.CartridgeException;
import com.quest.nitro.service.cartridge.api.Component;
import com.quest.nitro.service.cartridge.api.Dependency;
import com.quest.nitro.service.cartridge.api.DependencyMatchType;
import com.quest.nitro.service.cartridge.api.Identity;
import com.quest.nitro.service.cartridge.api.Incompatible;
import com.quest.nitro.service.cartridge.api.Relationship;
import com.quest.nitro.service.cartridge.api.Supersede;
import com.quest.nitro.service.derivation.ComplexDerivationDefinition;
import com.quest.nitro.service.derivation.DerivationService;
import com.quest.nitro.service.registry.RegistryService;
import com.quest.nitro.service.registry.RegistryVariable;
import com.quest.nitro.service.registry.RegistryVariableNotFoundException;
import com.quest.nitro.service.rule.rule.Rule;
import com.quest.nitro.service.rule.service.RuleService;
import com.quest.nitro.service.schedule.Schedule;
import com.quest.nitro.service.schedule.service.NamedSchedule;
import com.quest.nitro.service.schedule.service.ScheduleNotFoundException;
import com.quest.nitro.service.schedule.service.ScheduleService;
import com.quest.nitro.service.sl.impl.scriptAgent.ScriptAgentConstants;
import com.quest.nitro.service.sl.interfaces.ServiceLayerException;
import com.quest.nitro.service.sl.jibx.FoglightConfiguration;
import com.quest.nitro.service.util.JmxHelper;
import com.quest.nitro.service.wcf.WCFPersistenceManagerService;
import com.quest.nitro.service.wcf.WCFPersistenceManagerServiceMBean;

public class CartridgeExporterImpl implements CartridgeExporter {

	private static final String CI_SCHEDULE_FILE = "schedules.xml";
	private static final String CI_RULES_FILE = "rules.xml";
	private static final String CI_METRICS_FILE = "metrics.xml";
	private static final String CI_REGISTRY_FILE = "registry.xml";
	private TopologyObject _car;
	private String msg = "";
	private final Date now = new Date();

	public CartridgeExporterImpl(TopologyObject carDefinition) {
		_car = carDefinition;
	}

	@Override
	public String toString() {

		return "CartridgeExporter for Cartridge m:" + _car.getString("name");
	}

	public String createCartridge(String version) throws Exception {
		return createCartridge(version, new File("state" + File.separator + "fglide" + File.separator + "cartridgeExports"));
	}

	public String createCartridgeContent(String version, OutputStream os) throws Exception {
		appendMsg("Creating Cartridge " + _car.getString("name") + "   (Content only)");

		File tmpDir = createTempDir();
		File ciDir = new File(tmpDir, "res");
		ciDir.mkdirs();
		File wcfDir = new File(tmpDir, "wcf");
		wcfDir.mkdirs();

		// Write the Export Files
		writeExportFiles(version, tmpDir, ciDir);

		ZipTool.zipDirectory(tmpDir.getPath(), os);

		appendMsg("Finish building cartridge");
		return msg;

	}

	public String createCartridge(String version, File path) throws Exception {
		appendMsg("Creating Cartridge " + _car.getString("name") + "   (Version:" + version + ") in Directory "
				+ path.getAbsolutePath());

		File tmpDir = createTempDir();
		File ciDir = new File(tmpDir, "res");
		ciDir.mkdirs();
		File wcfDir = new File(tmpDir, "wcf");
		wcfDir.mkdirs();

		// Write the Export Files
		writeExportFiles(version, tmpDir, ciDir);

		File exportDir = new File("state" + File.separator + "fglide" + File.separator + "cartridgeExports");
		exportDir.mkdirs();

		exportCartridge(version, ciDir, wcfDir, exportDir);
		appendMsg("Finish building cartridge");
		return msg;
	}

	private void exportCartridge(String version, File ciDir, File wcfDir, File exportDir) throws CartridgeException {
		// create Car file
		CartridgeBuilder builder = new CartridgeBuilder(exportDir);

		// Create service cartridge
		Cartridge cartridge = new Cartridge(new Identity(_car.getString("name"), version, _car.getString("author")));

		addMPFile(version, ciDir, cartridge, "rules", CI_RULES_FILE);
		addMPFile(version, ciDir, cartridge, "schedules", CI_SCHEDULE_FILE);
		addMPFile(version, ciDir, cartridge, "metrics", CI_METRICS_FILE);
		addMPFile(version, ciDir, cartridge, "regvals", CI_REGISTRY_FILE);

		addWCF(version, wcfDir, cartridge);

		cartridge.setFinalFlag(true);

		addDependencies(cartridge);

		builder.addCartridge(cartridge);

		builder.build();
	}

	private void addDependencies(Cartridge cartridge) {
		List<Object> deps = _car.getList("dependencies");

		for (Object o : deps) {
			DataObject data = (DataObject) o;
			Relationship r = null;
			switch (data.getInt("type/value")) {
			case 0:
				r = new Dependency(data.getString("carName"), data.getString("version"), DependencyMatchType.valueOf(data
						.getString("MatchType/name")));

				break;
			case 1:
				r = new Incompatible(data.getString("carName"), data.getString("version"), DependencyMatchType.valueOf(data
						.getString("MatchType/name")));

				break;
			case 2:

				r = new Supersede(data.getString("carName"), data.getString("version"), DependencyMatchType.valueOf(data
						.getString("MatchType/name")));

				break;
			}

			cartridge.getRelationshipList().add(r);

		}
	}

	private void addWCF(String version, File wcfDir, Cartridge cartridge) {

		Component component = new Component(new Identity("WCF", version, _car.getString("author")),
				ScriptAgentConstants.CART_COMPONENT_TYPE_WCF);

		CartFile cartFile = CartFile.create("wcf", wcfDir);

		component.getItems().add(cartFile);
		component.setDeploymentItem(cartFile);

		for (File f : wcfDir.listFiles()) {
			
			addRecurseFile("wcf", f, component);
		}

		cartridge.getComponentList().add(component);

	}

	private void addRecurseFile(String path, File f, Component component) {

		String newPath = path + '/' + f.getName();

		CartFile cartFileItem = CartFile.create(newPath, f);

		component.getItems().add(cartFileItem);

		if (f.isDirectory()) {
			for (File child : f.listFiles()) {
				addRecurseFile(newPath, child, component);
			}
		}
	}

	private void addMPFile(String version, File ciDir, Cartridge cartridge, String elementName, String filename) {
		Component component = new Component(new Identity(elementName, version, _car.getString("author")),
				ScriptAgentConstants.CART_COMPONENT_TYPE_MP);

		CartFile cartFile = new CartFile(filename, ciDir.getAbsolutePath() + File.separator + filename);

		component.getItems().add(cartFile);
		component.setDeploymentItem(cartFile);
		cartridge.getComponentList().add(component);
	}

	private void writeExportFiles(String version, File expDir, File ciDir) throws Exception, MalformedObjectNameException,
			RegistryVariableNotFoundException, JiBXException, FileNotFoundException, CartridgeException {

		for (Object obj : _car.getList("definitionGroups")) {
			TopologyObject group = (TopologyObject) obj;

			String name = group.getString("name");
			List objs = group.getList("includes");

			appendMsg("Exporting :" + name + " (" + objs.size() + ")");

			if (name.equals("rules"))
				expRules(objs, ciDir);
			else if (name.equals("metrics"))
				expMetrics(objs, ciDir);
			else if (name.equals("registryEntries"))
				expRegistry(objs, ciDir);
			else if (name.equals("schedules"))
				expSchedules(objs, ciDir);
			else if (name.equals("wcf"))
				expWCF(objs, expDir);
		}

	}

	private void expSchedules(List objs, File ciDir) throws Exception {
		ScheduleService rsbean = (ScheduleService) JmxHelper.getMBeanImpl(MBeanServerLocator.locateJBoss(), new ObjectName(
				"com.quest.nitro:service=Schedule"));
		File rFile = new File(ciDir, CI_SCHEDULE_FILE);

		List rules = new ArrayList<Rule>();

		for (Object obj : objs) {
			try {
				DataObject schedule = (DataObject) obj;
				appendMsg("Exporting " + schedule.getString("name") + "  :" + stripId(schedule.getString("ci_id")));

				NamedSchedule r = rsbean.get(stripId(schedule.getString("ci_id")), now);
				appendMsg("Schedule :" + r);
				rules.add(r);
			} catch (Exception e) {
				appendMsg("!!!! Problems while Loading " + e);
			}

		}
		appendMsg("Writing Rule.xml Export file.");

		IBindingFactory bfact = BindingDirectory.getFactory(FoglightConfiguration.class);

		IMarshallingContext mctx = bfact.createMarshallingContext();

		mctx.setIndent(4);

		FoglightConfiguration config = new FoglightConfiguration();
		// latest version always
		config.setVersion("1.0.1.0");

		config.setConfigItems(rules);
		mctx.marshalDocument(config, "UTF-8", null, new FileOutputStream(rFile));

	}

	protected void expWCF(List objs, File tmpDir) throws CartridgeException {
		try {
			WCFPersistenceManagerService wcfBean = (WCFPersistenceManagerService) JmxHelper.getMBeanImpl(MBeanServerLocator
					.locateJBoss(), new ObjectName("com.quest.nitro:service=WCFPersistenceManager"));

			if (objs.size() == 0)
				return;

			String[] modules = new String[objs.size()];

			int i = 0;
			for (Object obj : objs) {
				DataObject data = (DataObject) obj;
				modules[i++] = data.getString("ci_id");
			}

			wcfBean.exportModules(modules, tmpDir.getAbsolutePath() + File.separator + "wcfRes.zip");
			ZipTool.unzip(new File(tmpDir, "wcfRes.zip"), new File(tmpDir, "wcf"));

		} catch (Exception e) {
			throw new CartridgeException(e);
		}

	}

	protected void expRegistry(List objs, File tmpDir) throws MalformedObjectNameException, NullPointerException,
			RegistryVariableNotFoundException, JiBXException, FileNotFoundException {
		RegistryService rsbean = (RegistryService) JmxHelper.getMBeanImpl(MBeanServerLocator.locateJBoss(), new ObjectName(
				"com.quest.nitro:service=Registry"));
		File rFile = new File(tmpDir, CI_REGISTRY_FILE);

		List rules = new ArrayList<Rule>();

		for (Object obj : objs) {
			try {
				DataObject rule = (DataObject) obj;
				appendMsg("Exporting " + rule.getString("name") + "  :" + stripId(rule.getString("ci_id")));

				RegistryVariable r = rsbean.get(stripId(rule.getString("ci_id")), now);
				appendMsg("Registry :" + r);
				rules.add(r);
			} catch (Exception e) {
				appendMsg("!!!! Problems while Loading " + e);
			}

		}

		appendMsg("Writing Registry.xml Export file. (" + rFile.getAbsolutePath() + ")");

		IBindingFactory bfact = BindingDirectory.getFactory(FoglightConfiguration.class);

		IMarshallingContext mctx = bfact.createMarshallingContext();

		mctx.setIndent(4);

		FoglightConfiguration config = new FoglightConfiguration();
		// latest version always
		config.setVersion("1.0.1.0");

		config.setConfigItems(rules);
		mctx.marshalDocument(config, "UTF-8", null, new FileOutputStream(rFile));

	}

	protected void expMetrics(List objs, File tmpDir) throws Exception {

		DerivationService rsbean = (DerivationService) JmxHelper.getMBeanImpl(MBeanServerLocator.locateJBoss(), new ObjectName(
				"com.quest.nitro:service=Derivation"));
		File rFile = new File(tmpDir, CI_METRICS_FILE);

		List cis = new ArrayList<Rule>();

		for (Object obj : objs) {
			try {
				DataObject ci = (DataObject) obj;
				appendMsg("Exporting " + ci.getString("name") + "  :" + stripId(ci.getString("ci_id")));

				ComplexDerivationDefinition r = rsbean.get(stripId(ci.getString("ci_id")), now);
				appendMsg("Metric :" + r);
				cis.add(r);
			} catch (Exception e) {
				appendMsg("!!!! Problems while Loading " + e);
			}

		}
		appendMsg("Writing metrics.xml Export file.  (" + rFile.getAbsolutePath() + ")");

		IBindingFactory bfact = BindingDirectory.getFactory(FoglightConfiguration.class);

		IMarshallingContext mctx = bfact.createMarshallingContext();

		mctx.setIndent(4);

		FoglightConfiguration config = new FoglightConfiguration();
		// latest version always
		config.setVersion("1.0.1.0");

		config.setConfigItems(cis);
		mctx.marshalDocument(config, "UTF-8", null, new FileOutputStream(rFile));

	}

	protected void expRules(List objs, File tmpDir) throws Exception {

		RuleService rsbean = (RuleService) JmxHelper.getMBeanImpl(MBeanServerLocator.locateJBoss(), new ObjectName(
				"com.quest.nitro:service=Rule"));
		File rFile = new File(tmpDir, CI_RULES_FILE);

		List rules = new ArrayList<Rule>();

		for (Object obj : objs) {
			try {
				DataObject rule = (DataObject) obj;
				appendMsg("Exporting " + rule.getString("name") + "  :" + stripId(rule.getString("ci_id")));

				Rule r = rsbean.get(stripId(rule.getString("ci_id")), now);
				appendMsg("Rule :" + r);
				rules.add(r);
			} catch (Exception e) {
				appendMsg("!!!! Problems while Loading " + e);
			}

		}
		appendMsg("Writing Rule.xml Export file.");

		IBindingFactory bfact = BindingDirectory.getFactory(FoglightConfiguration.class);

		IMarshallingContext mctx = bfact.createMarshallingContext();

		mctx.setIndent(4);

		FoglightConfiguration config = new FoglightConfiguration();
		// latest version always
		config.setVersion("1.0.1.0");

		config.setConfigItems(rules);
		mctx.marshalDocument(config, "UTF-8", null, new FileOutputStream(rFile));

	}

	private String stripId(String id) {
		return id.substring(id.lastIndexOf('/') + 1);
	}

	private void appendMsg(String string) {
		msg += string + "\n";
	}

	private File createTempDir() throws IOException {
		File tmpDir = new File(System.getProperty("java.io.tmpdir") + File.separator + _car.getString("name") + "_tmp_"
				+ System.currentTimeMillis());
		appendMsg("Create Temp :" + tmpDir.getAbsolutePath());

		if (tmpDir.exists()) {
			FileUtils.forceDelete(tmpDir);
		}

		tmpDir.mkdirs();

		prepareDir(tmpDir);
		return tmpDir;
	}

	protected void prepareDir(File tmpDir) {

	}

}
