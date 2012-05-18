package system._devExpTTT.scripts;
def result = server.CartridgeService.installCar(carFile)

StringBuffer msg = new StringBuffer()

if (result.hasError()) {
msg << "Cartridge install finished with Errors: \n$result.errorString"
} else msg << "Cartridge Install finished. \n"

msg <<"---------------------------------\n"
msg <<"Cartridges Installed : \n"
result.installedCartridgeIds.each {
server.CartridgeService.activateCartridge(it)
sleep(2000)
def c= server.CartridgeService.getCartridge(it)
msg << "    --> $it (${c.cartridgeStatus})\n"
}

return msg.toString()
