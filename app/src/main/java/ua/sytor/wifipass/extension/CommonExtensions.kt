package ua.sytor.wifipass.extension

fun String.trimQuotes(): String {
	var str = this
	if (str.startsWith("\""))
		str = substring(1)
	if (str.endsWith("\""))
		str = str.substring(0, str.length - 1)
	return str
}