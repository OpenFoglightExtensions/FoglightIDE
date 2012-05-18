package system._devExpTTT.scripts;
def type = server.TopologyService.getType("IDE_CAR_Dependency")

def obj = server.TopologyService.getObjectShell(type)

obj.set("carName",depCar.name)
obj.set("version",version)
obj.set("type",depType)
obj.set("MatchType",matchType)
obj.set("container",car)

def c = server.TopologyService.getObjectShell(server.TopologyService.getType("IDE_CAR_ExportContainer"))

c.set("name",car.get("name"))
c.getList("dependencies").add(obj)
server.TopologyService.mergeData([obj,c])

return car