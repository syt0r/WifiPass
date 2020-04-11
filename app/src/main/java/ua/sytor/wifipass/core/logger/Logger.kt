package ua.sytor.wifipass.core.logger

import android.util.Log

class Logger {

	companion object {

		private const val TAG = "WifiPass"

		fun log(message: String) {
			Log.d(TAG, message)
		}

	}

}