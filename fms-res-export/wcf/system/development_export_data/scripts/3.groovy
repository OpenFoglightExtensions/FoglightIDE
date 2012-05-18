package system.development_export_data.scripts;
println "TESTER\n"

try {
def qs = server.QueryService
println("DEBUG:Executing Query "+"!"+queryDefinition.get("query",null))

def stmt= qs.getQueryDefinition( "!"+queryDefinition.get("query",null))


def erg =  qs.performQuery( stmt).topologyObjects
println "Result:"+erg
return erg
} catch (Throwable t) {

return []}
