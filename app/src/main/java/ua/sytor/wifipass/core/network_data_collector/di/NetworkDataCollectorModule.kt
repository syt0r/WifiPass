package ua.sytor.wifipass.core.network_data_collector.di

import org.koin.dsl.module
import ua.sytor.wifipass.core.network_data_collector.NetworkDataCollector
import ua.sytor.wifipass.core.network_data_collector.NetworkDataCollectorContract

val networkDataCollectorModule = module {
	factory<NetworkDataCollectorContract.Collector> { NetworkDataCollector(get()) }
}