package system._development_export_scripts.scripts;
def _car = car
def msg = new StringBuffer()

// Imports
import org.apache.commons.io.FileUtils;
import com.quest.nitro.service.cartridge.api.*


File tmpDir = new File(System.getProperty("java.io.tmpdir") + 
		File.separator + _car.getString("name") + 
		"_tmp_"	+ System.currentTimeMillis());
if (tmpDir.exists()) {
	FileUtils.forceDelete(tmpDir);
}
tmpDir.mkdirs();

msg.append("Exporting to Cartridge !!! (tmp:"+tmpDir+"\n----------------------\n");

// create Cartridge File
File exportDir = new File("state" + File.separator + "fglide" + File.separator + "cartridgeExports");
exportDir.mkdirs();
CartridgeBuilder builder = new CartridgeBuilder(exportDir);

// Create service cartridge
Cartridge cartridge = new Cartridge(new Identity(_car.getString("name"), version, _car.getString("author")));

// create a free Cartridge by Default
cartridge.setType("free")

msg.append("Cartidge Name :"+_car.getString("name")+"\nVersion: "+version+"\n");

cartridge.setFinalFlag(true);

_car.getList("dependencies").each{ data->
			Relationship r = null;
			switch (data.getInt("type/value")) {
			case 0:
				r = new Dependency(data.getString("carName"), data.getString("version"), DependencyMatchType.valueOf(data
						.getString("MatchType/name")));

				break;
			case 1:
				r = new Incompatible(data.getString("carName"), data.getString("version"), DependencyMatchType.valueOf(data
						.getString("MatchType/name")));

				break;
			case 2:

				r = new Supersede(data.getString("carName"), data.getString("version"), DependencyMatchType.valueOf(data
						.getString("MatchType/name")));

				break;
			}

			cartridge.getRelationshipList().add(r);

		}

// call all closures
_car.definitionGroups.expToCar.findAll {it != null}.each{ cl->
cl.call(tmpDir,cartridge,version,msg) }

// Build !!!
builder.addCartridge(cartridge);
builder.build();


return msg.toString()