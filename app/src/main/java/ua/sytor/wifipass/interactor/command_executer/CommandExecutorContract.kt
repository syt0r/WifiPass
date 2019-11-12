package ua.sytor.wifipass.interactor.command_executer


interface CommandExecutorContract {

    interface CommandExecutor {

        @Throws(Exception::class)
        fun execCommand(command: String): String

        @Throws(Exception::class)
        fun execCommand(command: String, timeout: Long): String

    }

}
