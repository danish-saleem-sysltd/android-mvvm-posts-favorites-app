package com.example.mvvm.postsfavorites.feature.auth.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvvm.postsfavorites.core.ui.components.AppButton
import com.example.mvvm.postsfavorites.core.ui.components.AppTextField
import com.example.mvvm.postsfavorites.core.ui.theme.AppTheme
import com.example.mvvm.postsfavorites.core.ui.theme.spacing
import com.example.mvvm.postsfavorites.feature.auth.R
import com.example.mvvm.postsfavorites.feature.auth.ui.screen.login.entity.LoginState
import com.example.mvvm.postsfavorites.feature.auth.ui.screen.login.entity.LoginUiEvent

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            viewModel.onEvent(LoginUiEvent.NavigationHandled)
            onLoginSuccess()
        }
    }

    LoginContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun LoginContent(
    state: LoginState,
    onEvent: (LoginUiEvent) -> Unit,
) {
    Scaffold(containerColor = MaterialTheme.colorScheme.background) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = MaterialTheme.spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(MaterialTheme.spacing.xxxl))

            LoginHeader()

            Spacer(Modifier.height(MaterialTheme.spacing.xxl))

            AppTextField(
                value = state.email,
                onValueChange = { onEvent(LoginUiEvent.EmailChanged(it)) },
                label = stringResource(R.string.auth_login_email_label),
                keyboardType = KeyboardType.Email,
                isError = state.emailHasError,
                errorText = if (state.emailHasError) stringResource(R.string.auth_login_email_error) else null,
            )
            Spacer(Modifier.height(MaterialTheme.spacing.md))
            AppTextField(
                value = state.password,
                onValueChange = { onEvent(LoginUiEvent.PasswordChanged(it)) },
                label = stringResource(R.string.auth_login_password_label),
                keyboardType = KeyboardType.Password,
                isPassword = true,
                isError = state.passwordHasError,
                errorText = if (state.passwordHasError) stringResource(R.string.auth_login_password_error) else null,
            )
            Spacer(Modifier.height(MaterialTheme.spacing.xl))
            AppButton(
                text = stringResource(R.string.auth_login_submit),
                onClick = { onEvent(LoginUiEvent.Submit) },
                enabled = state.isSubmitEnabled,
            )
        }
    }
}

@Composable
private fun LoginHeader() {
    Box(
        modifier = Modifier
            .size(96.dp)
            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Lock,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(44.dp),
        )
    }
    Spacer(Modifier.height(MaterialTheme.spacing.lg))
    Text(
        text = stringResource(R.string.auth_login_title),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground,
    )
    Spacer(Modifier.height(MaterialTheme.spacing.xxs))
    Text(
        text = stringResource(R.string.auth_login_subtitle),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Preview
@Composable
private fun PreviewLoginScreenEmpty() {
    AppTheme { LoginContent(state = LoginState(), onEvent = {}) }
}

@Preview
@Composable
private fun PreviewLoginScreenValid() {
    AppTheme {
        LoginContent(
            state = LoginState(
                email = "user@mail.com",
                password = "password1",
                isSubmitEnabled = true,
            ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun PreviewLoginScreenError() {
    AppTheme {
        LoginContent(
            state = LoginState(
                email = "abc",
                password = "abc",
                emailHasError = true,
                passwordHasError = true,
            ),
            onEvent = {},
        )
    }
}
