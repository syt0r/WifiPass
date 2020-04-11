package ua.sytor.wifipass.screen.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.dialog_check_password.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ua.sytor.wifipass.R
import ua.sytor.wifipass.core.logger.Logger

class SplashFragment : Fragment() {

	private val viewModel by viewModel<SplashViewModel>()

	private val observer = Observer<State> { state ->
		Logger.log("state[$state]")
		when (state) {
			State.Init -> {
				viewModel.loadRoute()
			}

			State.Loading -> {

			}

			State.WaitingForPassword -> {
				showPasswordCheckDialog()
			}

			State.CheckSuccessfullyCompleted -> {
				navigateNext()
			}

			State.CheckFailed -> {
				Toast.makeText(context, R.string.splash_screen_wrong_pass_message, Toast.LENGTH_LONG).show()
				exitApp()
			}
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.subscribeOnState().observe(this, observer)
	}

	private fun navigateNext() {
		Logger.log("navigateNext")
		findNavController().navigate(R.id.splash_to_wifi_action)
	}

	private fun showPasswordCheckDialog() {
		Logger.log("showPasswordCheckDialog")
		val view = LayoutInflater.from(context).inflate(R.layout.dialog_check_password, null)
		AlertDialog.Builder(context!!)
			.setTitle(R.string.check_password_dialog_title)
			.setView(view)
			.setCancelable(false)
			.setPositiveButton(R.string.check_password_dialog_positive_button) { _, _ ->
				viewModel.checkPassword(view.editText.text.toString())
			}
			.setNegativeButton(R.string.check_password_dialog_negative_button) { _, _ ->
				exitApp()
			}
			.show()
	}

	private fun exitApp() {
		Logger.log("exitApp")
		activity!!.finishAndRemoveTask()
	}

}