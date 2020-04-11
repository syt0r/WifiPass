package ua.sytor.wifipass.screen.splash.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ua.sytor.wifipass.screen.splash.SplashViewModel

val splashScreenModule = module {
	viewModel {
		SplashViewModel(get(), get())
	}
}