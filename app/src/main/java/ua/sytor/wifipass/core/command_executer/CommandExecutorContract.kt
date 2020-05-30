package ua.sytor.wifipass.core.command_executer


interface CommandExecutorContract {

    interface CommandExecutor {

        @Throws(Exception::class)
        suspend fun execCommand(cmdArray: Array<String>): String

        @Throws(Exception::class)
        suspend fun execCommand(cmdArray: Array<String>, timeout: Long): String

    }

}
