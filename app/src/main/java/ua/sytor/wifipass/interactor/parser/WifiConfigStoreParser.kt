package ua.sytor.wifipass.interactor.parser

import org.jsoup.Jsoup

import java.util.ArrayList

import ua.sytor.wifipass.model.WifiNetworkInfo
import ua.sytor.wifipass.utils.trimQuotes

class WifiConfigStoreParser : Parser {

    override fun parseFileContent(fileContent: String): List<WifiNetworkInfo> {

        val wifiList = ArrayList<WifiNetworkInfo>()

        val document = Jsoup.parse(fileContent)

        val networkTags = document.select("Network")
        for (networkTag in networkTags) {

            val ssidTag = networkTag.selectFirst("string[name='SSID']")
            val pskTag = networkTag.selectFirst("string[name='PreSharedKey']")
//            val keyManagmentTypeTag = networkTag.selectFirst("string[name='SSID']")
//            val priorityTag = networkTag.selectFirst("string[name='priority']")

            val ssid = ssidTag.text().trimQuotes()
            val psk = pskTag.text().trimQuotes()

            val wifi = WifiNetworkInfo(ssid, psk)
            wifiList.add(wifi)

        }

        return wifiList

    }

}
