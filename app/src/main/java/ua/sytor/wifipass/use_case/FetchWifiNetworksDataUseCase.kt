package ua.sytor.wifipass.use_case

import kotlinx.coroutines.flow.flow
import ua.sytor.wifipass.core.network_data_collector.NetworkDataCollectorContract

class FetchWifiNetworksDataUseCase(
	private val collector: NetworkDataCollectorContract.Collector
) {

	operator fun invoke() = flow {
		val result = collector.collect()
		emit(result)
	}

}