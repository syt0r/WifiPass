package ua.sytor.wifipass.screen.base

import androidx.lifecycle.ViewModel

abstract class BasePresenter<T: BaseView> : ViewModel() {

    var view: T? = null
    private set

    fun attachView(view: T) {
        this.view = view
    }

    fun detachView() {
        view = null
    }

}