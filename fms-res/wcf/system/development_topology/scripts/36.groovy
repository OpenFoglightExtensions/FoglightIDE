package system.development_topology.scripts;
if (concrete) return server.QueryService.queryTopologyObjects("$typename: topologyTypeName = '$typename'" as String).size()
else return server.QueryService.queryTopologyObjects("$typename" as String).size()