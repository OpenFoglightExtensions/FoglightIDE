package system._devExpTTT.scripts;
def f = new File("cartridge")
					// make sure the Path exists
					f.mkdirs()

				

					def cartridges = f.list().collect{it}.findAll{
					it.endsWith(".car") 
					}

					cartridges.collect{
                                       def name = it[0..it.lastIndexOf("-")-1]
					def version = it - name - "-" - ".car"
					def fileType = server.TopologyService.getType("IDE_CAR_ExportFile")
					def obj =
					server.TopologyService.createAnonymousDataObject(fileType)
					obj.set("version",version)
					obj.set("filename",it);
					def file = new File(f,it)
					obj.set("date", new Date(file.lastModified()))
					obj.set("size",file.length())
					obj.set("fullPath",file.absolutePath)

					return obj
					}	