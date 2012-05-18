package system._devExpTTT.scripts;
   def elementsToStore = ci




    def gType = server.TopologyService.getType("IDE_ExpGroup_WCF")

    def group =  server.TopologyService.getObjectShell(gType)
    group.set("container",car)
    group.set("name","wcf")
    


    def elements = elementsToStore.collect { moduleName->
     
      def e = server.TopologyService.getObjectShell(server.TopologyService.getType("IDE_CI_WCFDefinition"))

     e.set("ci_id",moduleName)
     e.set("name", moduleName)

     e.set("parent",group)     

     return e;
    }

    group.getList("includes").addAll(elements)
    
def createCar = { name ->
    def type = server.TopologyService.getType("IDE_CAR_ExportContainer")
    def obj = server.TopologyService.getObjectShell(type)
    obj.set("name",name)
return obj
}

def carCopy = createCar.call(car.get("name"))
carCopy.getList("definitionGroups").add(group)
    
    server.TopologyService.mergeData([group,elements,carCopy].flatten())


return car

