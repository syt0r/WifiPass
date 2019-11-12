package ua.sytor.wifipass.screen.wifi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

import ua.sytor.wifipass.R
import ua.sytor.wifipass.model.WifiNetworkInfo
import ua.sytor.wifipass.screen.wifi.ui.WifiNetworkListAdapter

class WifiScreenView : Fragment(), WifiScreenContract.View {

    val presenter: WifiScreenContract.Presenter by viewModel()

    private val adapter = WifiNetworkListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.wifi_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(currentScope.get { parametersOf(view) })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun showData(data: List<WifiNetworkInfo>) {

    }

    override fun showRawConfig(rawConfig: String) {

    }

}
