package system.development_export_cieditors_topologyexports.scripts;
def addElement(def groupname) {
    def gType = server.TopologyService.getType("IDE_Delegated_Group")

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

  

     
      def e = server.TopologyService.getObjectShell(server.TopologyService.getType("IDE_CI_ExpTypes"))
       e.set("service", service)
       e.set("includeContainments",includeContainments)     

e.set("parent",group)
e.set("ci_id",service.get("name"))
   group.getList("includes").add(e)

    
    server.TopologyService.mergeData([group,e,carCopy].flatten())

}

addElement ("DataExport")
return car
