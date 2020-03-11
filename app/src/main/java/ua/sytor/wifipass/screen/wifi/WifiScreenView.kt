package ua.sytor.wifipass.screen.wifi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ua.sytor.wifipass.R
import ua.sytor.wifipass.core.parser.Parser
import ua.sytor.wifipass.screen.wifi.ui.WifiNetworkListAdapter

class WifiScreenView : Fragment() {

    private val viewModel by viewModel<WifiScreenViewModel>()

    private val adapter = WifiNetworkListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.wifi_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun showData(data: List<Parser.WifiNetworkData>) {

    }

    private fun showRawConfig(rawConfig: String) {

    }

}
