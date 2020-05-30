package ua.sytor.wifipass.core.network_data_collector

import ua.sytor.wifipass.core.command_executer.CommandExecutorContract
import ua.sytor.wifipass.core.logger.Logger
import ua.sytor.wifipass.core.network_data_collector.NetworkDataCollectorContract.CollectingResult

class NetworkDataCollector(
	private val commandExecutor: CommandExecutorContract.CommandExecutor
) : NetworkDataCollectorContract.Collector {

	override suspend fun collect(): CollectingResult {
		Logger.log("collect")
		if (!isRooted()) {
			return CollectingResult.Failure(CollectingResult.FailureReason.NO_ROOT_ACCESS)
		}

		val (configPath, configOptions) = configs
			.filter { (filePath, _) ->
				isConfigurationFileExists(filePath)
			}
			.asSequence()
			.firstOrNull()
			?: return CollectingResult.Failure(CollectingResult.FailureReason.CONFIG_NOT_FOUND)


		val fileContent = readFileContent(configPath)
			?: return CollectingResult.Failure(CollectingResult.FailureReason.UNKNOWN)

		val data = configOptions.parser.parseFileContent(fileContent)

		return CollectingResult.Success(data, fileContent)
	}

	private suspend fun isRooted(): Boolean {
		return try {
			commandExecutor.execCommand(
				cmdArray = arrayOf("su", "-c", "ls"),
				timeout = 1000
			)
			true
		} catch (e: Exception) {
			false
		}
	}

	private suspend fun isConfigurationFileExists(path: String): Boolean {
		return try {
			val output = commandExecutor.execCommand(
				cmdArray = arrayOf("su", "-c", "test -e $path && echo 1 || echo 0"),
				timeout = 1000
			)
			output.contains("1")
		} catch (e: Exception) {
			false
		}
	}

	private suspend fun readFileContent(path: String): String? {
		return commandExecutor.execCommand(
			cmdArray = arrayOf("su", "-c", "cat $path"),
			timeout = 1000
		)
	}


}