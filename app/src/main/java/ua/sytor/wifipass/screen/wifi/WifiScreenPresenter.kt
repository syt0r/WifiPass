package ua.sytor.wifipass.screen.wifi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.sytor.wifipass.interactor.command_executer.CommandExecutorContract
import ua.sytor.wifipass.interactor.file_reader.FileReaderContract
import java.lang.Exception

class WifiScreenPresenter(
        private val commandExecutor: CommandExecutorContract.CommandExecutor,
        private val fileReader: FileReaderContract.FileReader
) : WifiScreenContract.Presenter() {

    private val state = MutableLiveData<WifiScreenContract.State>()

    init {
        state.observeForever {
            when(it) {

                is WifiScreenContract.State.LOADING -> {}


            }
        }
    }

    override fun loadData() {

        state.value = WifiScreenContract.State.LOADING
        viewModelScope.launch(Dispatchers.IO) {

            checkRootAccess()

        }

    }

    override fun showRawConfig() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun checkRootAccess() {
        try {
            val output = commandExecutor.execCommand("su", 1000)
        } catch (e: Exception) {
            state.postValue(WifiScreenContract.State.NO_ROOT_ACCESS)
        }
    }

    private fun checkConfigFileExists() {

    }

}
