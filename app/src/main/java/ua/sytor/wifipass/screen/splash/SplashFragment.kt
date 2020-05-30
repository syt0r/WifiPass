package ua.sytor.wifipass.screen.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.dialog_check_password.*
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
		val view = LayoutInflater.from(context).inflate(R.layout.dialog_check_password, null)

		val dialog = AlertDialog.Builder(requireContext(), R.style.AlertDialog)
			.setTitle(R.string.check_password_dialog_title)
			.setView(view)
			.setPositiveButton(R.string.check_password_dialog_positive_button) { _, _ -> }
			.setNegativeButton(R.string.check_password_dialog_negative_button) { _, _ -> }
			.create()

		val onPositiveClick: (View) -> Unit = {
			val password = view.passwordEditText.text.toString()
			val isValid = viewModel.checkPassword(password)
			if (isValid) {
				dialog.cancel()
			} else {
				dialog.passwordInputLayout.error = it.context.getString(R.string.check_password_error_text)
				view.passwordInputLayout.passwordEditText.setText("")
			}
		}

		dialog.show()

		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(onPositiveClick)
		dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener { exitApp() }
	}

	private fun exitApp() {
		Logger.log("exitApp")
		requireActivity().finishAndRemoveTask()
	}

}