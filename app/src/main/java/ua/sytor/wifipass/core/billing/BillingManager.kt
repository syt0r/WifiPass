package ua.sytor.wifipass.core.billing

import android.app.Activity
import android.app.Application
import com.android.billingclient.api.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import ua.sytor.wifipass.core.billing.BillingContract.BillingQueryResult
import ua.sytor.wifipass.core.billing.BillingContract.PurchaseResult
import ua.sytor.wifipass.core.logger.Logger

@ExperimentalCoroutinesApi
class BillingManager(
	application: Application
) : BillingContract.BillingManager, PurchasesUpdatedListener {

	private val billingClient: BillingClient = BillingClient.newBuilder(application)
		.enablePendingPurchases()
		.setListener(this)
		.build()

	private val billingQueryResultChannel = BroadcastChannel<BillingQueryResult>(Channel.CONFLATED)
	private val purchaseResultChannel = Channel<PurchaseResult>(Channel.CONFLATED)

	init {
		Logger.log("billingClient start connection")
		billingClient.startConnection(object : BillingClientStateListener {
			override fun onBillingSetupFinished(billingResult: BillingResult) {
				Logger.log("onBillingSetupFinished billingResult[$billingResult]")
				if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
					queryPurchases()
				}
			}

			override fun onBillingServiceDisconnected() {
				Logger.log("onBillingServiceDisconnected")
			}
		})
	}

	override fun queryAvailablePurchases() = billingQueryResultChannel.openSubscription().consumeAsFlow()

	override fun makePurchase(activity: Activity, skuDetails: SkuDetails): Flow<PurchaseResult> {
		Logger.log("makePurchase skuDetails[$skuDetails]")
		val flowParams = BillingFlowParams.newBuilder()
			.setSkuDetails(skuDetails)
			.build()

		val billingResult = billingClient.launchBillingFlow(activity, flowParams)
		return purchaseResultChannel.consumeAsFlow()
	}

	override fun onPurchasesUpdated(billingResult: BillingResult?, purchases: MutableList<Purchase>?) {
		val isSuccess = billingResult?.responseCode == BillingClient.BillingResponseCode.OK ||
				billingResult?.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED
		purchaseResultChannel.offer(if (isSuccess) PurchaseResult.Success else PurchaseResult.Fail)
	}

	private fun queryPurchases() {
		val skuDetailParams = SkuDetailsParams.newBuilder()
			.setSkusList(skuList)
			.setType(BillingClient.SkuType.INAPP)
			.build()
		CoroutineScope(Dispatchers.IO).launch {
			val queryResult = billingClient.querySkuDetails(skuDetailParams)
			Logger.log("queryResult[$queryResult]")
			val result = when (queryResult.billingResult.responseCode) {
				BillingClient.BillingResponseCode.OK -> {
					BillingQueryResult.Success(queryResult.skuDetailsList ?: emptyList())
				}
				else -> {
					BillingQueryResult.Fail
				}
			}
			billingQueryResultChannel.offer(result)
		}

	}

}