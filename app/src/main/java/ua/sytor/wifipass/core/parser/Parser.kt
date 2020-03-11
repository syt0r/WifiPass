package ua.sytor.wifipass.core.parser

import android.os.Build

interface Parser {

	fun parseFileContent(fileContent: String): List<WifiNetworkData>

	data class WifiNetworkData(
		val ssid: String,
		val psk: String,
		val keyManagementType: String = "Unknown",
		val priority: Int = 0
	)

	companion object {

		fun create(): Parser {
			return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
				WifiConfigStoreParser()
			} else {
				WpaSupplicantParser()
			}
		}

	}

}
