package ua.sytor.wifipass.screen.wifi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.sytor.wifipass.core.command_executer.CommandExecutorContract
import ua.sytor.wifipass.core.file_reader.FileReaderContract
import ua.sytor.wifipass.core.parser.Parser.WifiNetworkData

sealed class State {

	object Init : State()
	object Loading : State()

	data class Loaded(
		val data: List<WifiNetworkData>
	) : State()

	data class Error(
		val throwable: Throwable
	) : State()

}

class WifiScreenViewModel(
	private val commandExecutor: CommandExecutorContract.CommandExecutor,
	private val fileReader: FileReaderContract.FileReader
) : ViewModel() {

	private val state = MutableLiveData<State>()

	fun loadData() {

	}

	private fun showRawConfig() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	private fun checkRootAccess() {
		try {
			val output = commandExecutor.execCommand("su", 1000)
		} catch (e: Exception) {
			state.value = State.Error(Throwable("No Root Access"))
		}
	}

	private fun checkConfigFileExists() {

	}

}
