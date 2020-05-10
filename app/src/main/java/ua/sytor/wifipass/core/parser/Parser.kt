package ua.sytor.wifipass.core.parser

import ua.sytor.wifipass.core.parser.entity.WifiNetworkData

interface Parser {
	suspend fun parseFileContent(fileContent: String): List<WifiNetworkData>
}
