package com.example.mvvm.postsfavorites.feature.auth.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.postsfavorites.feature.auth.domain.repository.WriteAuthRepository
import com.example.mvvm.postsfavorites.feature.auth.domain.usecase.ValidateEmailUseCase
import com.example.mvvm.postsfavorites.feature.auth.domain.usecase.ValidatePasswordUseCase
import com.example.mvvm.postsfavorites.feature.auth.ui.screen.login.entity.LoginState
import com.example.mvvm.postsfavorites.feature.auth.ui.screen.login.entity.LoginUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val writeAuthRepository: WriteAuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> onEmailChanged(event.value)
            is LoginUiEvent.PasswordChanged -> onPasswordChanged(event.value)
            LoginUiEvent.Submit -> submit()
            LoginUiEvent.NavigationHandled -> _state.update { it.copy(loginSuccess = false) }
        }
    }

    private fun onEmailChanged(value: String) {
        _state.update { current ->
            val emailValid = validateEmail(value)
            val passwordValid = validatePassword(current.password)
            current.copy(
                email = value,
                emailHasError = value.isNotEmpty() && !emailValid,
                isSubmitEnabled = emailValid && passwordValid,
            )
        }
    }

    private fun onPasswordChanged(value: String) {
        _state.update { current ->
            val emailValid = validateEmail(current.email)
            val passwordValid = validatePassword(value)
            current.copy(
                password = value,
                passwordHasError = value.isNotEmpty() && !passwordValid,
                isSubmitEnabled = emailValid && passwordValid,
            )
        }
    }

    private fun submit() {
        val current = _state.value
        if (!current.isSubmitEnabled) return
        viewModelScope.launch {
            writeAuthRepository.saveSession(current.email)
            _state.update { it.copy(loginSuccess = true) }
        }
    }
}
