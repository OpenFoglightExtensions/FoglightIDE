package system.development_export_data.scripts;
def objs = obj.type.properties.findAll{it.isContainment() && it.isMany()}.collect{obj.getList(it)}.flatten()

def dataObject = server.TopologyService.getType("DataObject")
def topologyObject = server.TopologyService.getType("TopologyObject")

def types = definition.get("typeList",null).collect{server.TopologyService.getType(it)}

// only include Containments if this type is a full included type
if (types.find{type->type.isAssignableFrom(obj.type)} == null) objs = []


if (definition.get("includeContainments",null) == false ) objs = objs.findAll { types.find{type->type.isAssignableFrom(it.type)} != null }


// add all Identity
objs +=obj.type.properties.findAll{it.isIdentity() && it.isMany()}.findAll{topologyObject.isAssignableFrom( it.valueType)}.collect{obj.getList(it)}.flatten()
objs +=obj.type.properties.findAll{it.isIdentity() && !it.isMany()}.findAll{topologyObject.isAssignableFrom( it.valueType)}.collect{obj.get(it)}


return objs