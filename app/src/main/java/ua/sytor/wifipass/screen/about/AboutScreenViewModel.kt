package ua.sytor.wifipass.screen.about

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.android.billingclient.api.SkuDetails
import ua.sytor.wifipass.core.billing.BillingContract


class AboutScreenViewModel(
	private val billingManager: BillingContract.BillingManager
) : ViewModel() {

	fun getPurchaseOptions() = billingManager.queryAvailablePurchases()

	fun makePurchase(activity: Activity, skuDetails: SkuDetails) {
		billingManager.makePurchase(activity, skuDetails)
	}

	fun gotToSourceCode(activity: Activity) {
		val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(SOURCE_CODE_URL))
		activity.startActivity(browserIntent)
	}

	companion object {
		private const val SOURCE_CODE_URL = "https://github.com/SYtor/WifiPass"
	}

}