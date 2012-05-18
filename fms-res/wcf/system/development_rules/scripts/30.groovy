package system.development_rules.scripts;
try
{
	result = new StringBuilder(); 
	for (s in params) 
		result.append(s).append('&');
	return result.toString();
}
catch (Exception e)
{
}
return "";