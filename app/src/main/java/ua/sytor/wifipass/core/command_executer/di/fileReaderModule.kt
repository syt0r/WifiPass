package ua.sytor.wifipass.core.command_executer.di

import org.koin.dsl.module
import ua.sytor.wifipass.core.command_executer.CommandExecutor
import ua.sytor.wifipass.core.command_executer.CommandExecutorContract

val commandExecutorModule = module {
	factory<CommandExecutorContract.CommandExecutor> { CommandExecutor() }
}