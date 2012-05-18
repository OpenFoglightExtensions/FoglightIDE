package system.development_topology.scripts;
def l = new ArrayList()
l.addAll(server.QueryService.queryTopologyObjects(typename))
return l
