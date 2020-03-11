package ua.sytor.wifipass.screen.password.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ua.sytor.wifipass.screen.password.PasswordScreenViewModel

val passwordScreenModule = module {
	viewModel { PasswordScreenViewModel() }
}