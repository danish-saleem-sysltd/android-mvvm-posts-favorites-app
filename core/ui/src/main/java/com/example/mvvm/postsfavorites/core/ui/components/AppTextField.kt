package com.example.mvvm.postsfavorites.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.text.KeyboardOptions
import com.example.mvvm.postsfavorites.core.ui.theme.AppTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorText: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    singleLine: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(label) },
        isError = isError,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        supportingText = {
            if (isError && !errorText.isNullOrBlank()) {
                Text(text = errorText, color = MaterialTheme.colorScheme.error)
            }
        },
        shape = MaterialTheme.shapes.medium,
    )
}

@Preview
@Composable
private fun PreviewAppTextField() {
    AppTheme {
        AppTextField(
            value = "user@mail.com",
            onValueChange = {},
            label = "Email",
        )
    }
}

@Preview
@Composable
private fun PreviewAppTextFieldError() {
    AppTheme {
        AppTextField(
            value = "abc",
            onValueChange = {},
            label = "Email",
            isError = true,
            errorText = "Invalid email",
        )
    }
}
