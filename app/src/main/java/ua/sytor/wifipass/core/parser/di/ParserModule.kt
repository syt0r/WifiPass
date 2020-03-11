package ua.sytor.wifipass.core.parser.di

import org.koin.dsl.module
import ua.sytor.wifipass.core.parser.Parser

val parserModule = module {
	factory<Parser> { Parser.create() }
}