package ua.sytor.wifipass.model

data class WifiNetworkInfo(
    val ssid: String,
    val psk: String,
    val keyManagementType: String = "Unknown",
    val priority: Int = 0
)
