package ua.sytor.wifipass.core.parser

import org.jsoup.Jsoup
import ua.sytor.wifipass.core.parser.Parser.WifiNetworkData
import ua.sytor.wifipass.utils.trimQuotes
import java.util.*

class WifiConfigStoreParser : Parser {

	override fun parseFileContent(fileContent: String): List<WifiNetworkData> {

		val wifiList = ArrayList<WifiNetworkData>()

		val document = Jsoup.parse(fileContent)

		val networkTags = document.select("Network")
		for (networkTag in networkTags) {

			val ssidTag = networkTag.selectFirst("string[name='SSID']")
			val pskTag = networkTag.selectFirst("string[name='PreSharedKey']")
//            val keyManagmentTypeTag = networkTag.selectFirst("string[name='SSID']")
//            val priorityTag = networkTag.selectFirst("string[name='priority']")

			val ssid = ssidTag.text().trimQuotes()
			val psk = pskTag.text().trimQuotes()

			val wifi = WifiNetworkData(ssid, psk)
			wifiList.add(wifi)

		}

		return wifiList

	}

}
