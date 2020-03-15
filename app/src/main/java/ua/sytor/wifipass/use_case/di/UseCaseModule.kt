package ua.sytor.wifipass.use_case.di

import org.koin.dsl.module
import ua.sytor.wifipass.use_case.*

val useCaseModule = module {

	factory { CheckIsPasswordValidUseCase(get()) }
	factory { ClearPasswordUseCase(get()) }
	factory { FetchWifiNetworksDataUseCase(get()) }
	factory { IsPasswordProtectedUseCase(get()) }
	factory { SetPasswordUseCase(get()) }

}