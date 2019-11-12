package ua.sytor.wifipass.screen.password

import ua.sytor.wifipass.screen.base.BasePresenter
import ua.sytor.wifipass.screen.base.BaseView

interface PasswordScreenContract {

    interface View : BaseView {

    }

    abstract class Presenter : BasePresenter<View>() {

    }

}