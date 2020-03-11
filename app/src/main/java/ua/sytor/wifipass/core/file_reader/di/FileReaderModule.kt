package ua.sytor.wifipass.core.file_reader.di

import org.koin.dsl.module
import ua.sytor.wifipass.core.file_reader.FileReader
import ua.sytor.wifipass.core.file_reader.FileReaderContract

val fileReaderModule = module {
	factory<FileReaderContract.FileReader> { FileReader(get()) }
}