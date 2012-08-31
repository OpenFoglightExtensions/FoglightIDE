package system._development_export_scripts.scripts;
def type = server.TopologyService.getType("IDE_CAR_ExportContainer")
def groupType = server.TopologyService.getType("IDE_CAR_CIDefinitionGroup")

def obj = server.TopologyService.getObjectShell(type)

name = name.replace(" ","_")
			.replace("/","_")
			.replace("\\","_")

obj.set("name",name)
obj.set("author",author)
obj.set("description",description)

def groups = ["rules","metrics","registryEntries","schedules","wcf"].collect{n->
 def group =  server.TopologyService.getObjectShell(groupType)
 group.set("container",obj)
 group.set("name",n)
return group
}


def l = obj.getList("definitionGroups")
// Removed due to restructure (StefanM)
//l.addAll(groups)
return server.TopologyService.mergeData([obj,groups].flatten())[0]