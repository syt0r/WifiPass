package ua.sytor.wifipass.core.file_reader

import ua.sytor.wifipass.core.command_executer.CommandExecutorContract

class FileReader(
	private val commandExecutor: CommandExecutorContract.CommandExecutor
) : FileReaderContract.FileReader {

	@Throws(Exception::class)
	override suspend fun checkIsFileExists(filePath: String, su: Boolean): Boolean {
		val command = "[ -f $filePath ] && echo 1 || echo 0"
		val result = commandExecutor.execCommand(if (su) "su -c $command" else command)
		return result == "1"
	}

	override suspend fun readFileContents(filePath: String, su: Boolean): String {
		val command = "cat $filePath"
		return commandExecutor.execCommand(if (su) "su -c $command" else command)
	}

}
