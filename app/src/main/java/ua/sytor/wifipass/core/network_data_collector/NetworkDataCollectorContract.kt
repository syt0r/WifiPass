package ua.sytor.wifipass.core.network_data_collector

import ua.sytor.wifipass.core.parser.WifiNetworkData

interface NetworkDataCollectorContract {

	interface Collector {
		suspend fun collect(): CollectingResult
	}

	sealed class CollectingResult {

		data class Success(
			val data: List<WifiNetworkData>,
			val rawData: String
		) : CollectingResult()

		data class Failure(
			val failureReason: FailureReason,
			val throwable: Throwable? = null
		) : CollectingResult()

		enum class FailureReason {
			NO_ROOT_ACCESS,
			CONFIG_NOT_FOUND,
			UNKNOWN
		}

	}

}