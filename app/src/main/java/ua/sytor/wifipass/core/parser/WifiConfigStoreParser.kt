package ua.sytor.wifipass.core.parser

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import ua.sytor.wifipass.core.parser.entity.WifiNetworkData
import ua.sytor.wifipass.extension.trimQuotes
import java.util.*

class WifiConfigStoreParser : Parser {

	override suspend fun parseFileContent(fileContent: String): List<WifiNetworkData> {
		val wifiList = ArrayList<WifiNetworkData>()

		val document = Jsoup.parse(fileContent)

		val networkTags = document.select("Network")
		for (networkTag in networkTags) {
			val ssidTag: Element? = networkTag.selectFirst("string[name='SSID']")
			val pskTag: Element? = networkTag.selectFirst("string[name='PreSharedKey']")

			val ssid = ssidTag?.text()?.trimQuotes()
			val psk = pskTag?.text()?.trimQuotes()

			if (ssid != null && psk != null) {
				val wifi = WifiNetworkData(ssid, psk)
				wifiList.add(wifi)
			}
		}

		return wifiList
	}

}
