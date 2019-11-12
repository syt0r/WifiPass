package ua.sytor.wifipass

import android.app.Application
import org.koin.core.context.startKoin
import ua.sytor.wifipass.di.repositoryModule

class WifiPassApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(repositoryModule)
        }

    }
}
