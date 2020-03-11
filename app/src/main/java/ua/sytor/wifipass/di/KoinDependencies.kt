package ua.sytor.wifipass.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import ua.sytor.wifipass.core.command_executer.di.commandExecutorModule
import ua.sytor.wifipass.core.file_reader.di.fileReaderModule
import ua.sytor.wifipass.core.network_data_collector.di.networkDataCollectorModule
import ua.sytor.wifipass.core.parser.di.parserModule
import ua.sytor.wifipass.repository.password.di.passwordRepositoryModule
import ua.sytor.wifipass.screen.about.di.aboutScreenModule
import ua.sytor.wifipass.screen.password.di.passwordScreenModule
import ua.sytor.wifipass.screen.router.di.routerScreenModule
import ua.sytor.wifipass.screen.wifi.di.wifiScreenModule

class KoinDependencies {

	companion object {

		private val repositories = setOf(
			passwordRepositoryModule
		)

		private val coreModules = setOf(
			commandExecutorModule,
			fileReaderModule,
			networkDataCollectorModule,
			parserModule
		)

		private val screens = setOf(
			aboutScreenModule,
			passwordScreenModule,
			routerScreenModule,
			wifiScreenModule
		)

		private val modules = setOf<Module>()
			.union(repositories)
			.union(coreModules)
			.union(screens)

		fun inject(application: Application) {
			startKoin {
				androidContext(application)
				modules(modules.toList())
			}
		}

	}

}