package ua.sytor.wifipass.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import ua.sytor.wifipass.core.billing.di.billingModule
import ua.sytor.wifipass.core.command_executer.di.commandExecutorModule
import ua.sytor.wifipass.core.file_reader.di.fileReaderModule
import ua.sytor.wifipass.core.network_data_collector.di.networkDataCollectorModule
import ua.sytor.wifipass.repository.password.di.passwordRepositoryModule
import ua.sytor.wifipass.screen.about.di.aboutScreenModule
import ua.sytor.wifipass.screen.splash.di.splashScreenModule
import ua.sytor.wifipass.screen.wifi.di.wifiScreenModule
import ua.sytor.wifipass.use_case.di.useCaseModule

class KoinDependencies {

	companion object {

		private val repositories = setOf(
			passwordRepositoryModule
		)

		private val coreModules = setOf(
			billingModule,
			commandExecutorModule,
			fileReaderModule,
			networkDataCollectorModule,
			useCaseModule
		)

		private val screens = setOf(
			aboutScreenModule,
			splashScreenModule,
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