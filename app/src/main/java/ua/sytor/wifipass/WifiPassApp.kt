package ua.sytor.wifipass

import android.app.Application
import ua.sytor.wifipass.di.KoinDependencies

class WifiPassApp : Application() {

	override fun onCreate() {
		super.onCreate()
		KoinDependencies.inject(this)
	}
}
