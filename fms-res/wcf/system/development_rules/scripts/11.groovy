package system.development_rules.scripts;
try
{
	def ruleTriggering = args['ruleTriggering'];
	if (ruleTriggering.get('is_event_driven',null))
	{
		return "Event Driven - Event Name: " + ruleTriggering.get('trigger_event'); 	
	}
	else if (ruleTriggering.get('is_time_driven',null))
	{
		String result = "Time Driven";
		if (ruleTriggering.get('is_trigger_without_data',null))
			result = result + ", trigger without data"; 
		return result; 
	}
	else if (ruleTriggering.get('is_data_driven',null))
	{
		return "Data Driven"; 
	}
	
}
//anything wrong
catch (Exception e)
{
		//eat
}
return null;