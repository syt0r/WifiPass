package ua.sytor.wifipass.core.billing

import android.app.Activity
import com.android.billingclient.api.SkuDetails
import kotlinx.coroutines.flow.Flow

interface BillingContract {

	interface BillingManager {
		fun queryAvailablePurchases(): Flow<BillingQueryResult>
		fun makePurchase(activity: Activity, skuDetails: SkuDetails): Flow<PurchaseResult>
	}

	sealed class BillingQueryResult {
		data class Success(val purchaseOptions: List<SkuDetails>) : BillingQueryResult()
		object Fail : BillingQueryResult()
	}

	sealed class PurchaseResult {
		object Success : PurchaseResult()
		object Fail : PurchaseResult()
	}

}

val skuList = listOf(
	"donation"
)