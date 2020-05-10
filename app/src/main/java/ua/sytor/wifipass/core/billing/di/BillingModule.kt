package ua.sytor.wifipass.core.billing.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ua.sytor.wifipass.core.billing.BillingContract
import ua.sytor.wifipass.core.billing.BillingManager

val billingModule = module(createdAtStart = true) {
	single<BillingContract.BillingManager> {
		BillingManager(androidApplication())
	}
}