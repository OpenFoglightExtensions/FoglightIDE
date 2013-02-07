package system._development_topology.scripts;
String typeName = name
def ts = server["TopologyService"]

public List getSupertypes(def aType){
  def types = []
  def i = 0;
  while (aType != null) {
    def sTypeData = server["TopologyService"].createAnonymousDataObject(server["TopologyService"].getType("IDE_Type_Base"))
    sTypeData.setString("name",aType.getName())
    sTypeData.setString("label",(i<10?"0":"")+i+":"+aType.getName())
    i++
    types.add(sTypeData)
    aType = aType.getSuperType()

  }
  return types
}

public def getIDEDef(String name) {
  def data = server["TopologyService"].createAnonymousDataObject(server["TopologyService"].getType("IDE_Type_Base"))
  sTypeData.setString("name",name)
  return data
}
public List getSubtypes(String aTypeName) {
    def ts = server["TopologyService"]
    return ts.getTypeNames().findAll{ name ->
       return ts.getType(name).getSuperType()!=null && ts.getType(name).getSuperType().getName().equals(aTypeName)
    }.collect{ name ->
   def bType = server["TopologyService"].createAnonymousDataObject(server["TopologyService"].getType("IDE_Type_Base"))
   bType.setString("name",name)

       return bType
    }
}
public List getProperties(def aType) {
   def erg = []
   def bType = server["TopologyService"].createAnonymousDataObject(server["TopologyService"].getType("IDE_Type_Base"))
   bType.setString("name",aType.getName())

   def labelPropName = aType.getAnnotationValue(server["TopologyService"].getType("LabelProperty"))
   return aType.getProperties().collect { prop->
        def propData = server["TopologyService"].createAnonymousDataObject(server["TopologyService"].getType("IDE_Type_Property"))
        propData.setString("name",prop.getName())
        
        def typeData = server["TopologyService"].createAnonymousDataObject(server["TopologyService"].getType("IDE_Type_Base"))
        typeData.setString("name",prop.getValueType().getName())
        propData.set("type",typeData)

        typeData = server["TopologyService"].createAnonymousDataObject(server["TopologyService"].getType("IDE_Type_Base"))
        typeData.setString("name",prop.getContainingType().getName())
        propData.set("definedIn",typeData)
       
        propData.set("unit",prop.getUnitName())
        propData.setBoolean("manyDef",prop.isMany())      
        propData.setBoolean("containmentDef",prop.isContainment())         
        propData.setBoolean("identityDef",prop.isIdentity())         
  def exprProp = prop.getAnnotations().findAll{it.getType().getName().equals("DerivationExpression")}.size()>0
  propData.setBoolean("exprProp",exprProp)
  if (exprProp) {
	  def value = prop.getAnnotationValue(server["TopologyService"].getType("DerivationExpression"))
  	  propData.set("exprValue",(value != null)?value:"")
  	  } else propData.set("exprValue","")
        
        propData.setBoolean("labelProp",prop.getName().equals(labelPropName))

        return propData
   }

}
public List getDirectProperties(def aType) {
   def erg = []
   def bType = server["TopologyService"].createAnonymousDataObject(server["TopologyService"].getType("IDE_Type_Base"))
   bType.setString("name",aType.getName())
    def labelPropName = aType.getAnnotationValue(server["TopologyService"].getType("LabelProperty"))
  return aType.getProperties().findAll { p->
      p.getContainingType().getName().equals(aType.getName())
   }.collect { prop->
        def propData = server["TopologyService"].createAnonymousDataObject(server["TopologyService"].getType("IDE_Type_Property"))
        propData.setString("name",prop.getName())
        
        def typeData = server["TopologyService"].createAnonymousDataObject(server["TopologyService"].getType("IDE_Type_Base"))
        typeData.setString("name",prop.getValueType().getName())
        propData.set("type",typeData)

        typeData = server["TopologyService"].createAnonymousDataObject(server["TopologyService"].getType("IDE_Type_Base"))
        typeData.setString("name",prop.getContainingType().getName())
        propData.set("definedIn",typeData)
       
        propData.setBoolean("manyDef",prop.isMany())      
        propData.setBoolean("containmentDef",prop.isContainment())         
        propData.setBoolean("identityDef",prop.isIdentity())         
  def exprProp = prop.getAnnotations().findAll{it.getType().getName().equals("DerivationExpression")}.size()>0
  propData.setBoolean("exprProp",exprProp)
  if (exprProp) {
	  def value = prop.getAnnotationValue(server["TopologyService"].getType("DerivationExpression"))
  	  propData.set("exprValue",(value != null)?value:"")
  	  } else propData.set("exprValue","")
        
        propData.setBoolean("labelProp",prop.getName().equals(labelPropName))

        return propData
   }

}

def type = server["TopologyService"].getType(typeName)

// create the Data holder
def typeData = server["TopologyService"].createAnonymousDataObject(server["TopologyService"].getType("IDE_Type"))

def time = ["start":System.currentTimeMillis()]

typeData.setString("name",typeName)

def supertypes = getSupertypes(type.getSuperType())
typeData.setList("supertypes",supertypes)
typeData.set("supertype",supertypes[0])

time["super"] = System.currentTimeMillis()

typeData.setList("properties",getProperties(type))

time["props"] = System.currentTimeMillis()

typeData.setList("directProperties",getDirectProperties(type))

time["dprops"] = System.currentTimeMillis()

typeData.setList("subtypes",getSubtypes(typeName))

time["sub"] = System.currentTimeMillis() 

def annoTypeProp = #!IDE_AnnotationType : name = 'Property'#.getTopologyObjects()[0]
def annoTypeType = #!IDE_AnnotationType : name = 'Type'#.getTopologyObjects()[0]

def anno = type.getAnnotations().collect { 
    def name = it.getName()
    def value = type.getAnnotationValue(it)
    
    def data =     ts.createAnonymousDataObject(ts.getType("IDE_Type_Annotation"))
    data.set("definedIn",type.getName())
    data.set("type","(Type Annotation)")
    data.set("typeEnum",annoTypeType )
    data.set("typeName",name)
    data.set("value",(value != null)?value.toString():"")
    return data
}

type.getProperties().each { prop ->
    anno.addAll(prop.getAnnotations().collect { 
    def name = it.getName()
    def value = prop.getAnnotationValue(it)
    
    def data =     ts.createAnonymousDataObject(ts.getType("IDE_Type_Annotation"))
    data.set("definedIn",prop.getName())
    data.set("type","(Property Annotation)")
data.set("typeEnum",annoTypeProp )
    data.set("typeName",name)
    data.set("value",(value != null)?value.toString():"")
    return data
})
}
typeData.set("annotations",anno)

time["anno"] = System.currentTimeMillis()

println("Debug:"+time)
println "Timings : "+ (time["super"]-time["start"])+" --> super:"+ (time["props"]-time["super"]) + "--> props" + 
           (time["dprops"]-time["props"])+ " --> dprops "+  (time["sub"]-time["dprops"]) + "--> anno" + 
           (time["anno"]-time["sub"])


return typeData
