package system.development_metrics.scripts;
def obsName = obs+"("
def tName = "DerivatioCalculation for "+type

import org.jboss.mx.util.MBeanServerLocator;
import com.quest.nitro.service.util.JmxHelper;
import javax.management.ObjectName;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

def rsbean = JmxHelper.getMBeanImpl (MBeanServerLocator.locateJBoss (), new ObjectName ("com.quest.nitro:service=Derivation"))

def diag = rsbean.diagnosticSnapshotAsString();



diag = diag.split("\n")

def level = 0;
def found = false
diag = diag.findAll {
      if (it.contains(obsName)) {
level = 1;      
}

if (level == 2) {
level = 3; 
found = true;
}
if (level == 1 && it.contains(tName)) {
level = 2;

}



if (it == "normal") {
level = 0;
found = false;
}
  return found
}

def diag2 = diag.join("\n")
def rinfo = diag2.split("DerivationRulette for ") as List
rinfo = rinfo - rinfo[0]

return rinfo.collect {
def topObj = it[0..it.indexOf(":")-1]
def msg = it

def obj = server.TopologyService.createAnonymousDataObject(server.TopologyService.getType("IDE_Rulette_base"))

obj.set("scopingID",topObj)
obj.set("scope",server.TopologyService.getObject(topObj))
obj.set("diagnostic",msg)
return obj
}

