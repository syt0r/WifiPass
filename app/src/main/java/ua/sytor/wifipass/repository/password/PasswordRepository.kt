package ua.sytor.wifipass.repository.password

import kotlinx.coroutines.flow.Flow

interface PasswordRepository {
    fun isPasswordProtected(): Flow<Boolean>
    fun savePassword(password: String): Flow<Unit>
    fun resetPassword(): Flow<Unit>
    fun checkPassword(password: String): Flow<Boolean>
}
