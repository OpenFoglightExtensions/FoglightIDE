package system.development_common.scripts;
if (obj == null) return ""

def t = obj.type

def annoProp = t.properties.find{ prop-> prop.annotations.name.contains("LabelProperty") }
if (annoProp != null) return obj.get(annoProp)

// secondly look for PK)
def idProps = t.properties.findAll{prop-> prop.isIdentity() }
if (idProps.size()>0) {
   def erg = ""
   idProps.each {p ->
      if (erg != "") erg +=", "
      erg += obj.getString(p)
        
   }
   return erg + "  ("+t.name+")"
}

return obj.toString()
