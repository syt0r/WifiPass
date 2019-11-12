package ua.sytor.wifipass.screen.router

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ua.sytor.wifipass.R
import ua.sytor.wifipass.repository.password.PasswordRepository

class RouterFragment : Fragment() {

    val passwordRepository: PasswordRepository by inject()

    private var routingJob: Job? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        routingJob?.cancel()
        routingJob = GlobalScope.launch(Dispatchers.IO) {

            val navController = NavHostFragment.findNavController(this@RouterFragment)

            if (passwordRepository.isPasswordProtected)
                navController.navigate(R.id.password_fragment)
            else
                navController.navigate(R.id.wifi_fragment)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.router_fragment, container, false)
    }

    override fun onStop() {
        super.onStop()
        routingJob?.cancel()
    }

}