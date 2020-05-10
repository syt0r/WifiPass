package ua.sytor.wifipass.core.logger

import android.util.Log
import ua.sytor.wifipass.BuildConfig

class Logger {

	companion object {

		private const val TAG = "WifiPass"

		fun log(message: String) {
			if (BuildConfig.DEBUG)
				Log.d(TAG, message)
		}

	}

}