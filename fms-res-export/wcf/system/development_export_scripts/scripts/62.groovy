package system._development_export_scripts.scripts;

def s = File.separator

def f = new File("state${s}fglide${s}definitions")
f.mkdirs()
def outfile = new File(f,"${name}.xml")

outfile.withOutputStream{out->fglide_SaveProject(name,out)}
return outfile.absolutePath
