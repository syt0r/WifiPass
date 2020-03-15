package ua.sytor.wifipass.screen.wifi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_wifi.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import ua.sytor.wifipass.R
import ua.sytor.wifipass.core.parser.WifiNetworkData
import ua.sytor.wifipass.screen.wifi.ui.WifiNetworkListAdapter

class WifiScreenView : Fragment() {

	private val viewModel by viewModel<WifiScreenViewModel>()

	private val adapter = WifiNetworkListAdapter()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_wifi, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.subscribeOnState().observe(
			viewLifecycleOwner,
			Observer { state ->
				when (state) {
					is State.Init -> viewModel.loadData()
					is State.Loading -> showLoading()
					is State.Loaded -> {
						showData(state.data.data)
						dismissLoading()
					}
					is State.Error -> {
						showError(state.message)
						dismissLoading()
					}
				}
			}
		)

		adapter.subscribeOnItemSelected()
			.onEach { (id, wifiData) ->
				when(id) {
					R.id.copy -> viewModel.savePasswordToClipboard(wifiData.psk)
					R.id.share -> viewModel.sharePassword(wifiData.psk)
				}
			}
			.launchIn(lifecycleScope)

	}

	private fun showLoading() {
		progressBar.visibility = View.VISIBLE
		recyclerView.visibility = View.INVISIBLE
	}

	private fun dismissLoading() {
		progressBar.visibility = View.GONE
		recyclerView.visibility = View.VISIBLE
	}

	private fun showData(data: List<WifiNetworkData>) {
		errorText.visibility = View.GONE
		retryButton.visibility = View.GONE
		adapter.setData(data)
	}

	private fun showError(message: String) {
		errorText.text = message
		errorText.visibility = View.VISIBLE
		retryButton.visibility = View.VISIBLE
	}

	private fun showRawConfig() {
		val rawConfig = viewModel.getRawConfig()

	}

}
