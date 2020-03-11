package ua.sytor.wifipass.screen.wifi.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ua.sytor.wifipass.screen.wifi.WifiScreenViewModel

val wifiScreenModule = module {
	viewModel { WifiScreenViewModel(get(), get()) }
}