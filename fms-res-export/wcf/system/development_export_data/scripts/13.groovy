package system.development_export_data.scripts;
def typeList = definition.get("typeList",null)
if (typeList == null) typeList = []

def newList = typeList - types 
definition.store("typeList",newList,null)

return definition