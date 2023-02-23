package ua.sytor.wifipass.core.network_data_collector

import ua.sytor.wifipass.core.command_executer.CommandExecutorContract
import ua.sytor.wifipass.core.logger.Logger
import ua.sytor.wifipass.core.network_data_collector.NetworkDataCollectorContract.CollectingResult

class NetworkDataCollector(
    private val commandExecutor: CommandExecutorContract.CommandExecutor
) : NetworkDataCollectorContract.Collector {

    companion object {
        private const val ROOT_CHECK_COMMAND = "ls"
    }

    override suspend fun collect(): CollectingResult {
        if (!isRooted()) {
            return CollectingResult.Failure(CollectingResult.FailureReason.NO_ROOT_ACCESS)
        }

        val (configPath, configOptions) = configs
            .filter { (filePath, _) ->
                isConfigurationFileExists(filePath)
            }
            .asSequence()
            .firstOrNull()
            ?: return CollectingResult.Failure(CollectingResult.FailureReason.CONFIG_NOT_FOUND)


        val fileContent = readFileContent(configPath)
            ?: return CollectingResult.Failure(CollectingResult.FailureReason.UNKNOWN)

        val data = configOptions.parser.parseFileContent(fileContent)

        return CollectingResult.Success(data, fileContent)
    }

    private suspend fun isRooted(): Boolean {
        return try {
            runRootCommand(ROOT_CHECK_COMMAND)
            true
        } catch (e: Exception) {
            Logger.log(e.toString())
            false
        }
    }

    private suspend fun isConfigurationFileExists(path: String): Boolean {
        return try {
            val output = runRootCommand("test -e $path && echo 1 || echo 0")
            output.contains("1")
        } catch (e: Exception) {
            Logger.log(e.toString())
            false
        }
    }

    private suspend fun readFileContent(path: String): String? {
        return kotlin.runCatching {
            runRootCommand("cat $path")
        }.getOrElse {
            Logger.log(it.toString())
            null
        }
    }

    private suspend fun runRootCommand(command: String): String {
        return kotlin.runCatching {
            commandExecutor.execCommand(
                cmdArray = arrayOf("su", "-c", command)
            )
        }.getOrElse {
            commandExecutor.execCommand(
                cmdArray = arrayOf("su", "0", command)
            )
        }
    }

}