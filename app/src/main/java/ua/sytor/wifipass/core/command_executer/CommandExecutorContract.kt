package ua.sytor.wifipass.core.command_executer


interface CommandExecutorContract {

    interface CommandExecutor {

        @Throws(Exception::class)
        suspend fun execCommand(command: String): String

        @Throws(Exception::class)
        suspend fun execCommand(command: String, timeout: Long): String

    }

}
