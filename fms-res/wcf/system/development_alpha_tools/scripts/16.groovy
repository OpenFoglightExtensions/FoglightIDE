package system.development_alpha_tools.scripts;
help (server.ScriptingService)

try {
def scriptSvc = server.ScriptingService
def para = scriptSvc.createScriptExecutionParams()

para.setScopingObject(scope) 
return scriptSvc.runScript(src,para)
} catch (Exception ex) {
return ex
}