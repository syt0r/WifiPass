package ua.sytor.wifipass.interactor.file_reader

import ua.sytor.wifipass.interactor.command_executer.CommandExecutorContract

class FileReader(private val commandExecutor: CommandExecutorContract.CommandExecutor) : FileReaderContract.FileReader {

    @Throws(Exception::class)
    override fun checkIsFileExists(filePath: String, su: Boolean): Boolean {
        val command = "[ -f $filePath ] && echo 1 || echo 0"
        val result = commandExecutor.execCommand(if (su) "su -c $command" else command)
        return result == "1"
    }

    override fun readFileContents(filePath: String, su: Boolean): String {
        val command = "cat $filePath"
        return commandExecutor.execCommand(if (su) "su -c $command" else command)
    }

}
