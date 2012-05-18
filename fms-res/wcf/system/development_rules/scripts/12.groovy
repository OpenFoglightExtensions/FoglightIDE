package system.development_rules.scripts;
try
{
	def adminDataSourceObject = args['adminDataSourceObject'];
	return adminDataSourceObject.getType().getName();
	
}
//anything wrong
catch (Exception e)
{
		//eat
}
return null;