package system._development_export_scripts.scripts;


def gType = server.TopologyService.getType("IDE_ExpGroup_CI")


def group =  server.TopologyService.getObjectShell(gType)
group.set("container",car)
group.set("name","thresholds")


def type = server.TopologyService.getType("IDE_CAR_ExportContainer")
def obj = server.TopologyService.getObjectShell(type)
obj.set("name",car.get("name"))

def carCopy = obj 

carCopy.getList("definitionGroups").add(group)    


   def rids = car.definitionGroups.find{it.name == "thresholds"}?.includes?.ci_id
   if (rids == null) rids = []

   def elementsToStore = ci.findAll{c -> !rids.contains(c.get("uniqueId"))}


    def elements = elementsToStore.collect { c->
     
      def e = server.TopologyService.getObjectShell(server.TopologyService.getType("IDE_CI_Threshold"))
       e.set("ci_id",c.get("uniqueId"))
       	e.set("name", c.get("topology_type")+"."+c.get("metric"))
       e.set("parent",group)     
      return e;
    }

    group.getList("includes").addAll(elements)


    server.TopologyService.mergeData([group,elements,carCopy].flatten())

return car