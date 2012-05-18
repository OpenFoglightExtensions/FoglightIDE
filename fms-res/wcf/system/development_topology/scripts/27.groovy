package system.development_topology.scripts;
def types = ["Metric"].collect {server.TopologyService.getType(it)}
obj.type.properties.findAll{!types.contains(it.type)}.name