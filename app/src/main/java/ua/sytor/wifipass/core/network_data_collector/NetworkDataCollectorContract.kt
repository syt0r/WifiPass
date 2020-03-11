package ua.sytor.wifipass.core.network_data_collector

import ua.sytor.wifipass.core.parser.Parser

interface NetworkDataCollectorContract {

	interface Collector {
		fun collect(): CollectingResult
	}

	sealed class CollectingResult {

		data class Success(
			val data: List<Parser.WifiNetworkData>
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