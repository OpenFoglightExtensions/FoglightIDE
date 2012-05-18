package system._devExpTTT.scripts;
def res = ""

strings.each {
 if (res != "") res += ", "
 res += it
}
return res