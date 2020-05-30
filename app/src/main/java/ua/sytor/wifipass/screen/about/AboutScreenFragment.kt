package ua.sytor.wifipass.screen.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import ua.sytor.wifipass.BuildConfig
import ua.sytor.wifipass.R
import ua.sytor.wifipass.core.billing.BillingContract

class AboutScreenFragment : Fragment() {

	private val viewModel by viewModel<AboutScreenViewModel>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_about, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		toolbar.setupWithNavController(findNavController())
		version.text = view.context.getString(R.string.about_screen_version, BuildConfig.VERSION_NAME)

		sourceCodeButton.setOnClickListener {
			viewModel.gotToSourceCode(requireActivity())
		}

		viewModel.getPurchaseOptions()
			.onEach { billingQueryResult ->
				when (billingQueryResult) {
					is BillingContract.BillingQueryResult.Success -> {
						showDonationLayout(billingQueryResult)
					}
				}
			}
			.launchIn(lifecycleScope)

		viewModel.purchaseResultsFlow()
			.onEach {
				when (it) {
					BillingContract.PurchaseResult.Success -> notifyPurchaseSuccess()
					BillingContract.PurchaseResult.Fail -> notifyPurchaseFailed()
				}
			}
			.launchIn(lifecycleScope)

	}

	private fun showDonationLayout(response: BillingContract.BillingQueryResult.Success) {
		donationLayout.visibility = View.VISIBLE
		donateButton.setOnClickListener {
			viewModel.makePurchase(requireActivity(), response.purchaseOptions.first())
		}
	}

	private fun notifyPurchaseSuccess() {
		Snackbar.make(requireView(), R.string.about_screen_purchase_success, Snackbar.LENGTH_LONG).show()
	}

	private fun notifyPurchaseFailed() {
		Snackbar.make(requireView(), R.string.about_screen_purchase_fail, Snackbar.LENGTH_LONG).show()
	}

}
