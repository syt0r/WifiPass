package ua.sytor.wifipass.screen.about.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ua.sytor.wifipass.screen.about.AboutScreenViewModel

val aboutScreenModule = module {
	viewModel { AboutScreenViewModel(get()) }
}