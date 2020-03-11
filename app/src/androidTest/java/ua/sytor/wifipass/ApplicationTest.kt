package ua.sytor.wifipass

import org.junit.Assert
import org.junit.Test
import ua.sytor.wifipass.core.command_executer.CommandExecutor
import ua.sytor.wifipass.core.command_executer.CommandExecutorContract

class ApplicationTest {

	var commandExecutor: CommandExecutorContract.CommandExecutor = CommandExecutor()

	@Test
	fun testCmdCommand() {
		var output: String? = null
		try {
			output = commandExecutor.execCommand("su -c cat /data/misc/wifi/wpa_supplicant.conf")
		} catch (e: Exception) {
			e.printStackTrace()
		}
		println("output $output")
		Assert.assertEquals(1, 1)
	}

	@Test
	fun testCommand() {

	}

	@Test
	fun testSu() {
	}

}