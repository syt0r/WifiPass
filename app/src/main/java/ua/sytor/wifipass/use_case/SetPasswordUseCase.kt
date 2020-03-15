package ua.sytor.wifipass.use_case

import kotlinx.coroutines.flow.flow
import ua.sytor.wifipass.repository.password.PasswordRepository

class SetPasswordUseCase(
	private val repository: PasswordRepository
) {

	operator fun invoke(password: String) = flow {
		repository.setPassword(password)
		emit(Unit)
	}

}