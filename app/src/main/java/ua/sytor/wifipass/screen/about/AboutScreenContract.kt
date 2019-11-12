package ua.sytor.wifipass.screen.about

import ua.sytor.wifipass.screen.base.BasePresenter
import ua.sytor.wifipass.screen.base.BaseView

interface AboutScreenContract {

    interface View : BaseView {
        fun showDonateOptions()
    }

    abstract class Presenter : BasePresenter<View>() {

    }

}