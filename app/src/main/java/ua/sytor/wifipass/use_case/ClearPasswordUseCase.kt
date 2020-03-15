package ua.sytor.wifipass.use_case

import kotlinx.coroutines.flow.flow
import ua.sytor.wifipass.repository.password.PasswordRepository

class ClearPasswordUseCase(
	private val repository: PasswordRepository
) {

	operator fun invoke() = flow {
		repository.resetPassword()
		emit(Unit)
	}

}