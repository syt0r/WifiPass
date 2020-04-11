package ua.sytor.wifipass.screen.wifi

import android.app.Activity
import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ua.sytor.wifipass.R
import ua.sytor.wifipass.core.logger.Logger
import ua.sytor.wifipass.core.network_data_collector.NetworkDataCollectorContract.CollectingResult
import ua.sytor.wifipass.core.network_data_collector.NetworkDataCollectorContract.CollectingResult.FailureReason
import ua.sytor.wifipass.use_case.FetchWifiNetworksDataUseCase
import ua.sytor.wifipass.use_case.SetPasswordUseCase

sealed class State {

	object Init : State()
	object Loading : State()

	data class Loaded(
		val data: CollectingResult.Success
	) : State()

	data class Error(
		val message: String
	) : State()

}

class WifiScreenViewModel(
	private val app: Application,
	private val fetchWifiNetworksDataUseCase: FetchWifiNetworksDataUseCase,
	private val setPasswordUseCase: SetPasswordUseCase
) : AndroidViewModel(app) {

	private val state = MutableLiveData<State>(State.Init)

	fun subscribeOnState(): LiveData<State> = state

	fun loadData() {

		if (state.value == State.Loading)
			return

		fetchWifiNetworksDataUseCase()
			.flowOn(Dispatchers.IO)
			.catch {
				Logger.log("on catch error[$it]")
				state.value = State.Error(it.message ?: "Unknown error")
			}
			.onStart {
				Logger.log("start loading")
				state.value = State.Loading
			}
			.onEach { result ->
				Logger.log("on result result[$result]")
				when (result) {
					is CollectingResult.Success -> {
						state.value = State.Loaded(result)
					}

					is CollectingResult.Failure -> {
						val message = when (result.failureReason) {
							FailureReason.NO_ROOT_ACCESS -> app.getString(
								R.string.wifi_screen_no_root
							)
							FailureReason.CONFIG_NOT_FOUND -> app.getString(
								R.string.wifi_screen_config_not_found
							)
							FailureReason.UNKNOWN -> app.getString(
								R.string.wifi_screen_unknown_failure,
								result.throwable
							)
						}
						state.value = State.Error(message)
					}
				}
			}
			.launchIn(viewModelScope)
	}

	fun getRawConfig(): String {
		return (state.value as State.Loaded).data.rawData
	}

	fun setNewPassword(pass: String) {
		setPasswordUseCase.invoke(pass)
			.onEach {
				Toast.makeText(app, R.string.wifi_screen_password_update, Toast.LENGTH_SHORT).show()
			}
			.launchIn(viewModelScope)
	}

	fun savePasswordToClipboard(password: String) {
		val manager = app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
		val clipData = ClipData.newPlainText("pass", password)
		manager.setPrimaryClip(clipData)
		Toast.makeText(app, R.string.wifi_screen_copied_to_clipboard, Toast.LENGTH_SHORT).show()
	}

	fun sharePassword(activity: Activity, password: String) {
		val sharingIntent = Intent(Intent.ACTION_SEND)
		sharingIntent.type = "text/plain"
		sharingIntent.putExtra(Intent.EXTRA_TEXT, password)
		activity.startActivity(
			Intent.createChooser(
				sharingIntent,
				app.getString(R.string.wifi_screen_share_password)
			)
		)
	}

}
