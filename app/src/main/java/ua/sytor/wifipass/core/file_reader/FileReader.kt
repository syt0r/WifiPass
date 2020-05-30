package ua.sytor.wifipass.core.file_reader

import ua.sytor.wifipass.core.command_executer.CommandExecutorContract

class FileReader(
	private val commandExecutor: CommandExecutorContract.CommandExecutor
) : FileReaderContract.FileReader {

	override suspend fun readFileContents(filePath: String, su: Boolean): String {
		val command = "cat $filePath"
		val cmdArgs = if (su) {
			arrayOf("su", "-c", command)
		} else {
			arrayOf(command)
		}
		return commandExecutor.execCommand(cmdArgs)
	}

}
