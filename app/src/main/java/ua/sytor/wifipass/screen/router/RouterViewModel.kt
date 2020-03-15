package ua.sytor.wifipass.screen.router

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import ua.sytor.wifipass.R
import ua.sytor.wifipass.use_case.IsPasswordProtectedUseCase

enum class NavigationRoute(val id: Int) {
	PASSWORD_SCREEN(R.id.router_to_pass_action),
	WIFI_SCREEN(R.id.router_to_wifi_action)
}

sealed class State {
	object Init : State()
	object Loading : State()
	data class Loaded(val route: NavigationRoute) : State()
}

class RouterViewModel(
	private val isPasswordProtectedUseCase: IsPasswordProtectedUseCase
) : ViewModel() {

	private val state = MutableLiveData<State>(State.Init)

	fun subscribeOnState(): LiveData<State> = state

	fun loadRoute() {

		if (state.value != State.Init)
			return

		isPasswordProtectedUseCase()
			.flowOn(Dispatchers.IO)
			.onStart { state.value = State.Loading }
			.onEach { isPasswordProtected ->
				val route = if (isPasswordProtected) {
					NavigationRoute.PASSWORD_SCREEN
				} else {
					NavigationRoute.WIFI_SCREEN
				}
				state.value = State.Loaded(route)
			}
			.launchIn(viewModelScope)

	}

}