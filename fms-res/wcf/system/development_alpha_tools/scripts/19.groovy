package system.development_alpha_tools.scripts;
def r = ""+result

//r.replace("</br>" ,"\n")
//r.replace("<br/>" ,"\n")
r = r.replace("</br>","<br/>")

return "<div>"+ r+"</div>"