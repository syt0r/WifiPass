package ua.sytor.wifipass.core.network_data_collector

import ua.sytor.wifipass.core.command_executer.CommandExecutorContract
import ua.sytor.wifipass.core.logger.Logger
import ua.sytor.wifipass.core.network_data_collector.NetworkDataCollectorContract.CollectingResult
import ua.sytor.wifipass.core.parser.Parser

class NetworkDataCollector(
	private val commandExecutor: CommandExecutorContract.CommandExecutor,
	private val parser: Parser
) : NetworkDataCollectorContract.Collector {

	companion object {
		private val configsPath = arrayOf(
			"/data/misc/wifi/wpa_supplicant.conf",
			"/data/misc/wifi/WifiConfigStore.xml"
		)
	}

	override suspend fun collect(): CollectingResult {
		Logger.log("collect")
		if (!isRooted()) {
			return CollectingResult.Failure(CollectingResult.FailureReason.NO_ROOT_ACCESS)
		}

		val configPath = configsPath.find { isConfigurationFileExists(it) }
			?: return CollectingResult.Failure(CollectingResult.FailureReason.CONFIG_NOT_FOUND)

		val fileContent = readFileContent(configPath)
			?: return CollectingResult.Failure(CollectingResult.FailureReason.UNKNOWN)

		val data = parser.parseFileContent(fileContent)

		return CollectingResult.Success(data, fileContent)
	}

	private suspend fun isRooted(): Boolean {
		return try {
			commandExecutor.execCommand("su -c ls", 1000)
			true
		} catch (e: Exception) {
			false
		}
	}

	private suspend fun isConfigurationFileExists(path: String): Boolean {
		return try {
			val output = commandExecutor.execCommand(
				command = "su -c test -e $path && echo 1 || echo 0",
				timeout = 1000
			)
			output.contains("1")
		} catch (e: Exception) {
			false
		}
	}

	private suspend fun readFileContent(path: String): String? {
		return commandExecutor.execCommand(
			command = "su -c cat $path",
			timeout = 1000
		)
	}


}