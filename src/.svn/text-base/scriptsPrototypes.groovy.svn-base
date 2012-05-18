def exp = createCarExporter(scope)
exp.toString()
exp.createCartridge("1.0.0")
------------

def car = scope
def name = car.name.toString()
def version = "1.0.0"

import org.jboss.mx.util.MBeanServerLocator;
import com.quest.nitro.service.util.JmxHelper;
import javax.management.ObjectName;
import java.io.ByteArrayOutputStream;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import com.quest.nitro.service.sl.jibx.FoglightConfiguration;
import java.io.FileOutputStream;


def tmpDir = new File( System.getProperty("java.io.tmpdir")
         +File.separator+name+"_"
         +version+"_"
         +System.currentTimeMillis())

tmpDir.mkdirs()


// will start to work on the following groups !!!
//[rules
// metrics
// registryEntries
// schedules
// wcf
//
// in car.definitionGroups

// RULES EXPORT
List rulesCIs = car.definitionGroups.find{it.name == "rules"}.getList("includes")




def rsbean = JmxHelper.getMBeanImpl (MBeanServerLocator.locateJBoss (), new ObjectName ("com.quest.nitro:service=Rule"))


rules =  rulesCIs.ci_id.collect{rid ->rsbean.get(rid-"/rule/",new Date())}

IBindingFactory bfact = BindingDirectory.getFactory(
				FoglightConfiguration.class);

		IMarshallingContext mctx = bfact.createMarshallingContext();

		mctx.setIndent(4);

		FoglightConfiguration config = new FoglightConfiguration();
		// latest version always
		config.setVersion("1.0.1.0");

		config.setConfigItems(rules);
mctx.marshalDocument(config, "UTF-8", null, new FileOutputStream(new File(tmpDir ,"rules.xml")))
	

return 1	
