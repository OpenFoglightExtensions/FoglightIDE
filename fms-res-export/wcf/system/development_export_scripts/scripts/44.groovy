package system._devExpTTT.scripts;
def subs = mod
def erg = []

while (subs.size > 0) {
erg.addAll(subs)
subs = subs.collect{ if (it != null) it.get("subModules",specificTimeRange)}.flatten()
}


return erg
