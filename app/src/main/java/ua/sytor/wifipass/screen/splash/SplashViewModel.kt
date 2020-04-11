package ua.sytor.wifipass.screen.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import ua.sytor.wifipass.use_case.CheckIsPasswordValidUseCase
import ua.sytor.wifipass.use_case.IsPasswordProtectedUseCase

sealed class State {
	object Init : State()
	object Loading : State()
	object WaitingForPassword : State()
	object CheckSuccessfullyCompleted : State()
	object CheckFailed : State()
}

class SplashViewModel(
	private val isPasswordProtectedUseCase: IsPasswordProtectedUseCase,
	private val checkIsPasswordValidUseCase: CheckIsPasswordValidUseCase
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
				state.value = when {
					isPasswordProtected -> State.WaitingForPassword
					else -> State.CheckSuccessfullyCompleted
				}
			}
			.launchIn(viewModelScope)
	}

	fun checkPassword(password: String) {
		checkIsPasswordValidUseCase.invoke(password)
			.onEach { isMatching ->
				if (isMatching)
					state.value = State.CheckSuccessfullyCompleted
				else
					state.value = State.CheckFailed
			}
			.launchIn(viewModelScope)
	}

}