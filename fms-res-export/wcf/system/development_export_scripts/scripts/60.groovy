package system._devExpTTT.scripts;
def objsToDelete = car.definitionGroups.findAll{
it.getList("includes").empty
} as Set
server.TopologyService.deleteObjects(objsToDelete)

return car