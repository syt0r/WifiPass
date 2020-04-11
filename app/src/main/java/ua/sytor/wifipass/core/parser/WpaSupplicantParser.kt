package ua.sytor.wifipass.core.parser

import java.util.ArrayList
import java.util.HashMap
import ua.sytor.wifipass.extension.trimQuotes

class WpaSupplicantParser : Parser {

    override suspend fun parseFileContent(fileContent: String): List<WifiNetworkData> {

        val networks = fileContent.split("network=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val networksInfo = ArrayList<Map<String, String>>()
        for (network in networks)
            if (network.contains("ssid"))
                networksInfo.add(readNetworkInfo(network))

        val result = ArrayList<WifiNetworkData>(networksInfo.size)

        for (map in networksInfo) {

            val ssid = getString(map, "ssid", "Unknown").trimQuotes()
            val psk = getString(map, "psk", "Unknown").trimQuotes()
            val keyManagementType = getString(map, "key_mgmt", "Unknown")
            val priority = getInteger(map, "priority", 0)

            val wifiNetworkInfo = WifiNetworkData(ssid, psk, keyManagementType, priority)

            result.add(wifiNetworkInfo)
        }

        return result
    }

    private fun readNetworkInfo(network: String): Map<String, String> {

        val networkInfo = HashMap<String, String>()

        val lines = network.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (line in lines) {
            val data = line.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (data.size == 2) {
                networkInfo[data[0].trim { it <= ' ' }] = data[1].trim { it <= ' ' }
            }
        }

        return networkInfo

    }

    private fun getString(networkInfoMap: Map<String, String>, key: String, defaultValue: String): String {

        return try {
            val str = networkInfoMap[key]
            str ?: defaultValue
        } catch (e: Exception) {
            defaultValue
        }

    }

    private fun getInteger(networkInfoMap: Map<String, String>, key: String, defaultValue: Int): Int {

        return try {
            Integer.parseInt(networkInfoMap[key]!!)
        } catch (e: Exception) {
            defaultValue
        }

    }

}
