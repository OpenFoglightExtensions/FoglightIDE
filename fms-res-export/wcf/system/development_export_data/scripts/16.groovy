package system.development_export_data.scripts;
def query = exportDefinition.get("query",null)
def includeContainments = exportDefinition.get("includeContainments",null)
def typeList = exportDefinition.get("typeList",null)

def dir = new File("../../state"+File.separator+"fglide"+File.separator+"dataExports")
dir.mkdirs();

def output = File.createTempFile("MyFile_", "."+format ,dir)
output.withOutputStream{ os->
	fglide_exportDataCmdlet(query,includeContainments?"true":"false",
  typeList,os,format)

return output.absolutePath


					}
