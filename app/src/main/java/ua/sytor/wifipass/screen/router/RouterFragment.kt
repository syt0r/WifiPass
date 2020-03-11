package ua.sytor.wifipass.screen.router

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RouterFragment : Fragment() {

	private val viewModel by viewModel<RouterViewModel>()

	private val observer = Observer<State> { state ->
		when (state) {
			is State.Init -> viewModel.loadRoute()
			is State.Loading -> {}
			is State.Loaded -> {
				val navController = NavHostFragment.findNavController(this)
				navController.navigate(state.route.id)
			}
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.subscribeOnState().observe(this, observer)
	}

}