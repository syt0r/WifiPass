package ua.sytor.wifipass.repository.password

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.flow

class SharedPrefPasswordRepository(context: Context) : PasswordRepository {

	private val sharedPreferences: SharedPreferences by lazy {
		context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
	}

	override fun isPasswordProtected() = flow {
		emit(sharedPreferences.contains(PASSWORD_KEY))
	}

	@SuppressLint("ApplySharedPref")
	override fun savePassword(password: String) = flow {
		sharedPreferences.edit()
				.putString(PASSWORD_KEY, password)
				.commit()
		emit(Unit)
	}

	@SuppressLint("ApplySharedPref")
	override fun resetPassword() = flow {
		sharedPreferences.edit()
				.remove(PASSWORD_KEY)
				.commit()
		emit(Unit)
	}

	override fun checkPassword(password: String) = flow {
		emit(password == sharedPreferences.getString(PASSWORD_KEY, null))
	}

	companion object {
		private const val PREF_NAME = "password_store"
		private const val PASSWORD_KEY = "password"
	}

}
