package system._devExpTTT.scripts;
if (parentNodes == null) return null;

def cleanParentNodes = new ArrayList(parentNodes);
cleanParentNodes.removeAll([null]);
return cleanParentNodes.size() == 0 ? null : action;