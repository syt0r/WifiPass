package ua.sytor.wifipass.screen.about

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.SkuDetails
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ua.sytor.wifipass.R
import ua.sytor.wifipass.core.billing.BillingContract

class AboutScreenViewModel(
	private val billingManager: BillingContract.BillingManager
) : ViewModel() {

	companion object {
		private const val SOURCE_CODE_URL = "https://github.com/SYtor/WifiPass"
	}

	private val purchaseResultChannel = Channel<BillingContract.PurchaseResult>(Channel.UNLIMITED)

	fun getPurchaseOptions() = billingManager.queryAvailablePurchases()

	fun makePurchase(activity: Activity, skuDetails: SkuDetails) {
		billingManager.makePurchase(activity, skuDetails)
			.onEach {
				purchaseResultChannel.offer(it)
			}
			.launchIn(viewModelScope)
	}

	fun gotToSourceCode(activity: Activity) {
		val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(SOURCE_CODE_URL))
		val chooserIntent = Intent.createChooser(
			browserIntent,
			activity.getString(R.string.about_screen_chooser_text)
		)
		activity.startActivity(chooserIntent)
	}

	fun purchaseResultsFlow(): Flow<BillingContract.PurchaseResult> {
		return purchaseResultChannel.consumeAsFlow()
	}

}