package com.quest.foglight.ide.backdoor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Set;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.jboss.mx.util.MBeanServerLocator;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;

import com.quest.nitro.service.rule.RuleNotFoundException;
import com.quest.nitro.service.rule.rule.Rulette;
import com.quest.nitro.service.rule.service.RuleServiceMBean;
import com.quest.nitro.service.sl.interfaces.query.errors.QueryVerificationException;
import com.quest.nitro.service.sl.jibx.FoglightConfiguration;
import com.quest.nitro.service.util.JmxHelper;

public class Backdoor {
  
	public static void timeOut() {
		try {
			Thread.sleep(1000);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Set<Rulette> getRulletes(String id) throws MalformedObjectNameException, NullPointerException, QueryVerificationException, RuleNotFoundException {
		RuleServiceMBean rs = (RuleServiceMBean) getMBean("com.quest.nitro:service=Rule");
		
		return rs.get(id).getRulettes();
	}
	
	public static String getFMSHome() {
		return new File(".").getAbsolutePath();
	}
	
	public static Object getMBean(String name) throws MalformedObjectNameException, NullPointerException {
		return JmxHelper.getMBeanImpl (MBeanServerLocator.locateJBoss (), new ObjectName (name)); 
	}
	
	
	
	public static String getAsXML(List<Object> ci) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();


		IBindingFactory bfact = BindingDirectory.getFactory(
				FoglightConfiguration.class);

		IMarshallingContext mctx = bfact.createMarshallingContext();

		mctx.setIndent(4);

		FoglightConfiguration config = new FoglightConfiguration();
		// latest version always
		config.setVersion("1.0.1.0");

		config.setConfigItems(ci);
		mctx.marshalDocument(config, "UTF-8", null, out);
		return out.toString();

	}
}
