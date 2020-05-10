package ua.sytor.wifipass.screen.wifi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_set_password.view.*
import kotlinx.android.synthetic.main.fragment_wifi.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ua.sytor.wifipass.R
import ua.sytor.wifipass.core.logger.Logger
import ua.sytor.wifipass.core.parser.entity.WifiNetworkData
import ua.sytor.wifipass.screen.wifi.ui.WifiNetworkListAdapter


class WifiScreenFragment : Fragment() {

	private val viewModel by viewModel<WifiScreenViewModel>()

	private val adapter = WifiNetworkListAdapter()

	private val observer = Observer<State> { state ->
		Logger.log("state[$state]")
		when (state) {
			is State.Init -> {
				hideNetworksList()
				hideError()
				hideLoading()
				disableRawSourceButton()
				viewModel.loadData()
			}
			is State.Loading -> {
				disableRawSourceButton()
				showLoading()
			}
			is State.Loaded -> {
				hideLoading()
				hideError()
				showNetworksList(state.data.data)
				enableRawSourceButton()
				toolbar.invalidate()
			}
			is State.Error -> {
				hideLoading()
				hideNetworksList()
				disableRawSourceButton()
				showError(state.message)
			}
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_wifi, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.subscribeOnState().observe(viewLifecycleOwner, observer)

		toolbar.apply {
			setOnMenuItemClickListener {
				when (it.itemId) {
					R.id.raw_config -> {
						showRawConfig()
						true
					}
					R.id.set_password -> {
						showSetPasswordDialog()
						true
					}
					R.id.about -> {
						findNavController().navigate(R.id.wifi_to_about_action)
						true
					}
					else -> false
				}
			}

			setupWithNavController(
				findNavController(),
				AppBarConfiguration(setOf(R.id.wifi_fragment))
			)
		}

		recyclerView.apply {
			layoutManager = LinearLayoutManager(context)
			adapter = this@WifiScreenFragment.adapter
		}

		adapter.onDropdownButtonClickListener = { dropdownView, wifi ->
			showDropdownMenu(dropdownView, wifi)
		}

		retryButton.setOnClickListener {
			viewModel.loadData()
		}
	}

	private fun showLoading() {
		Logger.log("showLoading")
		progressBar.visibility = View.VISIBLE
		recyclerView.visibility = View.INVISIBLE
		retryButton.visibility = View.GONE
		errorText.visibility = View.GONE
		toolbar.menu.findItem(R.id.raw_config).isEnabled = false
	}

	private fun hideLoading() {
		Logger.log("dismissLoading")
		progressBar.visibility = View.GONE
	}

	private fun showNetworksList(data: List<WifiNetworkData>) {
		recyclerView.visibility = View.VISIBLE
		adapter.setData(data)
	}

	private fun hideNetworksList() {
		recyclerView.visibility = View.INVISIBLE
	}

	private fun showError(message: String) {
		Logger.log("showError message[$message]")
		errorText.text = message
		errorText.visibility = View.VISIBLE
		retryButton.visibility = View.VISIBLE
	}

	private fun hideError() {
		errorText.visibility = View.GONE
		retryButton.visibility = View.GONE
	}

	private fun enableRawSourceButton() {
		toolbar.apply {
			menu.findItem(R.id.raw_config).isEnabled = true
			invalidate()
		}
	}

	private fun disableRawSourceButton() {
		toolbar.apply {
			menu.findItem(R.id.raw_config).isEnabled = false
			invalidate()
		}
	}


	private fun showDropdownMenu(dropdownView: View, wifi: WifiNetworkData) {
		val popupMenu = PopupMenu(requireContext(), dropdownView)
		popupMenu.inflate(R.menu.wifi_screen_popup)
		popupMenu.setOnMenuItemClickListener { menuItem ->
			when (menuItem.itemId) {
				R.id.copy -> viewModel.savePasswordToClipboard(wifi.password)
				R.id.share -> viewModel.sharePassword(requireActivity(), wifi.password)
			}
			true
		}
		popupMenu.show()
	}

	private fun showRawConfig() {
		Logger.log("showRawConfig")
		val rawConfig = viewModel.getRawConfig()
		AlertDialog.Builder(requireContext(), R.style.AlertDialog)
			.setTitle(R.string.raw_config_dialog_title)
			.setNegativeButton(R.string.raw_config_dialog_negative_button) { _, _ -> }
			.setMessage(rawConfig)
			.show()
	}

	private fun showSetPasswordDialog() {
		val view = LayoutInflater.from(context).inflate(R.layout.dialog_set_password, null)
		AlertDialog.Builder(requireContext(), R.style.AlertDialog)
			.setTitle(R.string.raw_config_dialog_title)
			.setView(view)
			.setPositiveButton(R.string.set_password_dialog_positive_button) { _, _ ->
				viewModel.setNewPassword(view.editText.text.toString())
			}
			.setNegativeButton(R.string.set_password_dialog_negative_button) { _, _ -> }
			.show()
	}

}
