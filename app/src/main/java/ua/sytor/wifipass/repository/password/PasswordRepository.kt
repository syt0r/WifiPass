package ua.sytor.wifipass.repository.password

interface PasswordRepository {
    val isPasswordProtected: Boolean
    fun savePassword(password: String)
    fun resetPassword()
    fun checkPassword(password: String)
}
