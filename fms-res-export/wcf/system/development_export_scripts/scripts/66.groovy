package system._devExpTTT.scripts;
files.each{
try {
new File(it).delete()
} catch (Throwable t) {}
}