package system._devExpTTT.scripts;

def s = File.separator

def f = new File("state${s}fglide${s}projects")
f.mkdirs()
def outfile = new File(f,"${name}_project.zip")

outfile.withOutputStream{out->exportCartridgeProject(name,out)}
return outfile.absolutePath