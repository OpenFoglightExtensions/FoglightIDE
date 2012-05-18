package system._development_export_scripts.scripts;

def addElement(def t, def groupname) {
    def gType = server.TopologyService.getType("IDE_ExpGroup_CI")

    def group =  server.TopologyService.getObjectShell(gType)
    group.set("container",car)
    group.set("name",groupname)

	def createCar = { name ->
    	def type = server.TopologyService.getType("IDE_CAR_ExportContainer")
    	def obj = server.TopologyService.getObjectShell(type)
    	obj.set("name",name)
		return obj
	}

	def carCopy = createCar.call(car.get("name"))
	carCopy.getList("definitionGroups").add(group)    

   	def rids = car.definitionGroups.find{it.name == groupname}?.includes?.ci_id
   	if (rids == null) rids = []

   	def elementsToStore = ci.findAll{c -> !rids.contains(c.get("uniqueId"))}


    def elements = elementsToStore.collect { c->
     
      def e = server.TopologyService.getObjectShell(server.TopologyService.getType(t))
       //def e = server.TopologyService.createAnonymousDataObject(server.TopologyService.getType(t))
       e.set("ci_id",c.get("uniqueId"))
       if (t == "IDE_CI_Threshold") {
   	e.set("name", c.get("topology_type")+"."+c.get("metric"))
       } else {

e.set("name", c.get("name"))
}
       e.set("parent",group)     
      return e;
    }
    group.getList("includes").addAll(elements)
    
    server.TopologyService.mergeData([group,elements,carCopy].flatten())

}

if (typeName == "IDE_Regval") {
    addElement("IDE_CI_RegistryValue","registryEntries")
}

if (typeName == "IDE_Rule") {
    addElement("IDE_CI_Rule","rules")
}


if (typeName == "IDE_Metrics") {
    addElement("IDE_CI_DerivedMetric","metrics")
}
if (typeName == "IDE_Schedule") {
    addElement("IDE_CI_Schedule","schedules")
}
if (typeName == "IDE_Threshold") {
    addElement("IDE_CI_Threshold","thresholds")
}


return car