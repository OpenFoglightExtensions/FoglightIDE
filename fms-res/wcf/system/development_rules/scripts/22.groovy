package system.development_rules.scripts;
if (enable==null) return rules
if (!typeName) return rules

def t = server.TopologyService.getType(typeName)
rules.findAll {rule->
         if (!rule.get("scoping_query",null) || rule.get("scoping_query",null)== "") return false
         def rt = server.QueryService.getScopingTopologyObjectType(rule.get("scoping_query",null))
        
        return  rt.isAssignableFrom(t) || (enable && t.isAssignableFrom(rt))

 }
