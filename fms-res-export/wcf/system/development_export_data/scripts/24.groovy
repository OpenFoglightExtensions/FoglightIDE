package system.development_export_data.scripts;
def objs = obj.type.properties.findAll{it.isContainment() && it.isMany()}.collect{obj.getList(it)}.flatten()

def dataObject = server.TopologyService.getType("DataObject")
def topologyObject = server.TopologyService.getType("TopologyObject")

def types = definition.get("typeList",null).collect{server.TopologyService.getType(it)}

if (types.find{type->type.isAssignableFrom(obj.type)} == null) return "Identity Only"
else return "Full Detail"
