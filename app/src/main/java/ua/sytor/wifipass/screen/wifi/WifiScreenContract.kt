package ua.sytor.wifipass.screen.wifi

import ua.sytor.wifipass.model.WifiNetworkInfo
import ua.sytor.wifipass.screen.base.BasePresenter
import ua.sytor.wifipass.screen.base.BaseView

interface WifiScreenContract {

    sealed class State {
        object LOADING : State()
        object NO_ROOT_ACCESS : State()
        object CONFIG_FILE_NOT_FOUND : State()
        data class DISPLAYING_DATA(val data: List<WifiNetworkInfo>) : State()
    }

    interface View : BaseView {
        fun showLoading()
        fun showData(data: List<WifiNetworkInfo>)
        fun showRawConfig(rawConfig: String)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun loadData()
        abstract fun showRawConfig()
    }

}