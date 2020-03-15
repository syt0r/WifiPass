package ua.sytor.wifipass.repository.password

interface PasswordRepository {
	suspend fun setPassword(password: String)
	suspend fun getPassword(): String?
	suspend fun resetPassword()
}
