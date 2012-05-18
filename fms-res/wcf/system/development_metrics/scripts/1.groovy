package system.development_metrics.scripts;


def t = server.TopologyService.getType(typeName)

def dsvc = server.DerivationService

def metrics = dsvc.getAllDerivationsInfo().findAll{ it.name == propName}
metrics.findAll{
         return it.calculationInfoList.find {
         def rt = server.QueryService.getScopingTopologyObjectType(it.domainQuery)
          t.isAssignableFrom(rt)


} != null
 }

return metrics.size != 0
