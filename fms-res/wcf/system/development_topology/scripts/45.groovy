package system._development_topology.scripts;
def type = server.TopologyService.getType(typename)

def types = server.TopologyService.getDescendentTypes(server.TopologyService.getType("DataObject")).toArray()


types.collect{t-> return t.getProperties().findAll{ it.containingType == t}.findAll{ prop ->
  (includeDirect && prop.valueType == type ) ||
  ( includeSuper && prop.valueType.isAssignableFrom(type) && (prop.valueType != type )) ||
  ( includeSub && type.isAssignableFrom(prop.valueType) && (prop.valueType != type ))
}
}.flatten().collect {prop->

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
        return propData

}