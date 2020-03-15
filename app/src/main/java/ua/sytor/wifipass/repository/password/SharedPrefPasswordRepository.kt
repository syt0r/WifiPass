package ua.sytor.wifipass.repository.password

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPrefPasswordRepository(context: Context) : PasswordRepository {

	private val sharedPreferences: SharedPreferences by lazy {
		context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
	}

	@SuppressLint("ApplySharedPref")
	override suspend fun setPassword(password: String) {
		sharedPreferences.edit()
			.putString(PASSWORD_KEY, password)
			.commit()
	}

	override suspend fun getPassword(): String? {
		return sharedPreferences.getString(PASSWORD_KEY, null)
	}

	@SuppressLint("ApplySharedPref")
	override suspend fun resetPassword() {
		sharedPreferences.edit()
			.remove(PASSWORD_KEY)
			.commit()
	}

	companion object {
		private const val PREF_NAME = "password_store"
		private const val PASSWORD_KEY = "password"
	}

}
