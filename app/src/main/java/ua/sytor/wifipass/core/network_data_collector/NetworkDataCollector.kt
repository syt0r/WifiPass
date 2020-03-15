package ua.sytor.wifipass.core.network_data_collector

import ua.sytor.wifipass.core.command_executer.CommandExecutorContract
import ua.sytor.wifipass.core.network_data_collector.NetworkDataCollectorContract.CollectingResult

class NetworkDataCollector(
	private val commandExecutor: CommandExecutorContract.CommandExecutor
) : NetworkDataCollectorContract.Collector {

	override suspend fun collect(): CollectingResult {

		if (!isRooted()) {
			return CollectingResult.Failure(CollectingResult.FailureReason.NO_ROOT_ACCESS)
		}

		return CollectingResult.Success(listOf(), "")

	}

	private suspend fun isRooted(): Boolean {
		return try {
			commandExecutor.execCommand("su", 1000)
			true
		} catch (e: Exception) {
			false
		}
	}

	private fun isFileExists(path: String): Boolean {
		TODO()
	}

}