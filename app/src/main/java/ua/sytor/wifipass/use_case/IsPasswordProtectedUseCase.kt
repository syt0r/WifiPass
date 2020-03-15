package ua.sytor.wifipass.use_case

import kotlinx.coroutines.flow.flow
import ua.sytor.wifipass.repository.password.PasswordRepository

class IsPasswordProtectedUseCase(
	private val repository: PasswordRepository
) {

	operator fun invoke() = flow {
		val password = repository.getPassword()
		emit(password != null)
	}

}