package ua.sytor.wifipass.core.command_executer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class CommandExecutor : CommandExecutorContract.CommandExecutor {

	@Throws(Exception::class)
	override suspend fun execCommand(command: String): String {
		return execCommand(command, 2000)
	}

	@Throws(Exception::class)
	override suspend fun execCommand(command: String, timeout: Long): String {
		val process = Runtime.getRuntime().exec(command)

		//TODO handle commands that doesn't cancel and block thread
		withTimeout(timeout) {
			process.waitFor()
		}

		val output = getStreamOutput(process.inputStream)
		val error = getStreamOutput(process.errorStream)

		return if (error.isEmpty())
			output
		else
			throw Exception(error)

	}

	@Throws(IOException::class)
	private fun getStreamOutput(inputStream: InputStream): String {

		val bufferedReader = BufferedReader(
			InputStreamReader(inputStream))

		val log = StringBuilder()
		var line = bufferedReader.readLine()
		while (line != null) {
			log.append(line + "\n")
			line = bufferedReader.readLine()
		}

		return log.toString()
	}

}
