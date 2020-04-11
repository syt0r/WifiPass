package ua.sytor.wifipass.screen.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_about.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ua.sytor.wifipass.BuildConfig
import ua.sytor.wifipass.R

class AboutScreenFragment : Fragment() {

	private val viewModel by viewModel<AboutScreenViewModel>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_about, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		toolbar.setupWithNavController(findNavController())
		version.text = view.context.getString(R.string.about_screen_version, BuildConfig.VERSION_NAME)
	}

}
