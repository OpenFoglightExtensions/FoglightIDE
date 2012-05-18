package system.development_rules.scripts;
import org.jboss.mx.util.MBeanServerLocator;
import com.quest.nitro.service.util.JmxHelper;
import javax.management.ObjectName;

def rsbean = JmxHelper.getMBeanImpl (MBeanServerLocator.locateJBoss (), new ObjectName ("com.quest.nitro:service=Rule"))
rsbean.getRuleRef(ruleID).getRulettes().size()