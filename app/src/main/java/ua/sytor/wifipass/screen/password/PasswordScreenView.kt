package ua.sytor.wifipass.screen.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ua.sytor.wifipass.R

class PasswordScreenView : Fragment() {

	private val viewModel by viewModel<PasswordScreenViewModel>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.password_fragment, container, false)
	}

}
