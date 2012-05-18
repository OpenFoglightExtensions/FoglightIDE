package system.development_alpha_tools.scripts;
if (result == null) return "no Results yet"

def r = result.toString()

r.replace("</br>" ,"\n")
r.replace("<br/>" ,"\n")
r.replace(":" ,"\n")

r =  "\njks\nlk\nkk"+r

 return new com.quest.wcf.core.types.RichText(r)
