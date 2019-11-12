package ua.sytor.wifipass.di

import org.koin.dsl.module
import ua.sytor.wifipass.interactor.command_executer.CommandExecutor
import ua.sytor.wifipass.interactor.command_executer.CommandExecutorContract
import ua.sytor.wifipass.interactor.file_reader.FileReader
import ua.sytor.wifipass.interactor.file_reader.FileReaderContract
import ua.sytor.wifipass.repository.password.PasswordRepository
import ua.sytor.wifipass.repository.password.SharedPrefPasswordRepository

val repositoryModule = module {

    single<CommandExecutorContract.CommandExecutor> { CommandExecutor() }
    single<FileReaderContract.FileReader> { FileReader(get()) }

    single<PasswordRepository> { SharedPrefPasswordRepository(get()) }

}