package ua.sytor.wifipass.repository.password

import android.content.Context
import android.content.SharedPreferences

class SharedPrefPasswordRepository(context: Context) : PasswordRepository {

    private val sharedPreferences: SharedPreferences = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override val isPasswordProtected: Boolean
        get() = sharedPreferences.contains(PASSWORD_KEY)

    override fun savePassword(password: String) {
        sharedPreferences.edit()
                .putString(PASSWORD_KEY, password)
                .apply()
    }

    override fun resetPassword() {
        sharedPreferences.edit().remove(PASSWORD_KEY).apply()
    }

    override fun checkPassword(password: String) {

    }

    companion object {
        private const val PREF_NAME = "password_store"
        private const val PASSWORD_KEY = "password"
    }

}
