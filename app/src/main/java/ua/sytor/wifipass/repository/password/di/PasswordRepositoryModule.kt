package ua.sytor.wifipass.repository.password.di

import org.koin.dsl.module
import ua.sytor.wifipass.repository.password.PasswordRepository
import ua.sytor.wifipass.repository.password.SharedPrefPasswordRepository

val passwordRepositoryModule = module {
    single<PasswordRepository> { SharedPrefPasswordRepository(get()) }
}