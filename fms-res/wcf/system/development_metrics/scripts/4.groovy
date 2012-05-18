package system.development_metrics.scripts;
def t = server.TopologyService.getType(typeName)

def dsvc = server.DerivationService

def metrics = dsvc.getAllDerivationsInfo().findAll{ it.name == propName}
metrics.findAll{
         return it.calculationInfoList.findAll{
         def rt = server.QueryService.getScopingTopologyObjectType(it.domainQuery)
          t.isAssignableFrom(rt)



} != null
 }

if ( metrics == null) return null

return metrics.id