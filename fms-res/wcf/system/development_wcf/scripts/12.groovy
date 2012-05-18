package system.development_wcf.scripts;
def path = m.name
while (m.	parentModule != null){
m = m.parentModule
path = m.name+"/"+path
}

return path