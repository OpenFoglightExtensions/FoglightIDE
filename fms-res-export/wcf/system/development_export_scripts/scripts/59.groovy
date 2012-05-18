package system._devExpTTT.scripts;
    def gType = server.TopologyService.getType(groupTypeName)

    def group =  server.TopologyService.getObjectShell(gType)
    group.set("container",car)
    group.set("name",groupName)

def createCar = { name ->
    def type = server.TopologyService.getType("IDE_CAR_ExportContainer")
    def obj = server.TopologyService.getObjectShell(type)
    obj.set("name",name)
return obj
}

def carCopy = createCar.call(car.get("name"))
carCopy.getList("definitionGroups").add(group)    
def elements = cis.collect{ ci->
      def e = server.TopologyService.getObjectShell(server.TopologyService.getType(typeName))
       e.set("ci_id",ci)
       e.set("name", ci)
       e.set("parent",group)     
return e
}
    group.getList("includes").addAll(elements)
    
    server.TopologyService.mergeData([group,elements,carCopy].flatten())

return car
