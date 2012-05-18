package system.development_wcf.scripts;
import com.quest.wcf.core.types.IconReference;
try
{

	return new IconReference (icon.get("fqId",null));
}
catch (Exception e)
{
	System.out.println(e);
}
return null;