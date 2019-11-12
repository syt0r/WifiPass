package ua.sytor.wifipass.interactor.parser

import ua.sytor.wifipass.model.WifiNetworkInfo

interface Parser {
    fun parseFileContent(fileContent: String): List<WifiNetworkInfo>
}
