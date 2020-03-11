package ua.sytor.wifipass.screen.router.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ua.sytor.wifipass.screen.router.RouterViewModel

val routerScreenModule = module {
	viewModel {
		RouterViewModel(get())
	}
}