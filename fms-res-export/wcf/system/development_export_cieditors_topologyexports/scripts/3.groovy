package system.development_export_cieditors_topologyexports.scripts;
def path = svc.parents
path.add(svc)
path.remove(0)

String p = ""
def b = false
path.each{ 
if (!b) b = true 
else p+="/"
p+=it.name
}

return p