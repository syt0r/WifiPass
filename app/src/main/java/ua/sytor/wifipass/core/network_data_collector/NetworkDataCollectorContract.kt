package ua.sytor.wifipass.core.network_data_collector

import ua.sytor.wifipass.core.parser.Parser
import ua.sytor.wifipass.core.parser.WifiConfigStoreParser
import ua.sytor.wifipass.core.parser.WpaSupplicantParser
import ua.sytor.wifipass.core.parser.entity.WifiNetworkData

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

data class ConfigOptions(val parser: Parser)

val configs = mapOf(
	"/data/misc/wifi/wpa_supplicant.conf" to ConfigOptions(WpaSupplicantParser()),
	"/data/misc/wifi/WifiConfigStore.xml" to ConfigOptions(WifiConfigStoreParser()),
	"/data/misc/apexdata/com.android.wifi/WifiConfigStore.xml" to ConfigOptions(WifiConfigStoreParser())
)
