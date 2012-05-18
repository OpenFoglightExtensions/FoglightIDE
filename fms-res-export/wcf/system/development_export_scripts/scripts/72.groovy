package system._devExpTTT.scripts;
// The following are special reserved parameters which are always available to scripts (see documentation for details)
//
// locale - the locale selected on the browser by the user
// resourceBundle - the java ResourceBundle for the current locale with strings loaded from the module's "strings.properties" file in the public directory
// specificTimeRange - the current specific time range that must be used to get property values from data objects
// functionHelper - the FunctionHelper instance for getting other information 
//
// Uncomment the following line to get a Log instance
// org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog("function id: " + functionHelper.getFunctionId());

def svc = server.TopologyService

def newObj = svc.getObjectShell(target.type)
newObj.type.properties.findAll{it.identity}.each{newObj.set(it.name,target.get(it.name))}

src.type.properties.each{newObj.set(it.name,src.get(it.name,null))}
return svc.mergeData(newObj)
