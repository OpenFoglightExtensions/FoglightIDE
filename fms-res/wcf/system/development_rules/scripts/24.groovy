package system.development_rules.scripts;
import org.jboss.mx.util.MBeanServerLocator;
import com.quest.nitro.service.util.JmxHelper;
import javax.management.ObjectName;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

def rsbean = JmxHelper.getMBeanImpl (MBeanServerLocator.locateJBoss (), new ObjectName ("com.quest.nitro:service=Rule"))

def tbean = JmxHelper.getMBeanImpl (MBeanServerLocator.locateJBoss (), new ObjectName ("com.quest.nitro:service=Topology"))

def rule = rsbean.getRuleRef(ruleID)

rule.rulettes.collect { rulette ->
def obj = server.TopologyService.createAnonymousDataObject(server.TopologyService.getType("IDE_Rulette"))

obj.set("scopingID",rulette.scopingTopologyObjectId)
obj.set("scope",server.TopologyService.getObject(rulette.scopingTopologyObjectId))
obj.set("resetTime",rulette.resetTime)
obj.set("lastFired",rulette.lastFireTime)
obj.set("lastHit",rulette.lastHitTime)
obj.set("lastMiss",rulette.lastMissTime)
obj.set("numFire",rulette.numFire)
obj.set("numHit",rulette.numHit)
obj.set("numMiss",rulette.numMiss)

def strBuffer = new ByteArrayOutputStream()
rulette.dumpInfo(tbean,new PrintStream(strBuffer))
obj.set("diagnostic",strBuffer.toString())
return obj

}
