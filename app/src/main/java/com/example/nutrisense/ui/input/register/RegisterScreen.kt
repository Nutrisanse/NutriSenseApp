package com.example.nutrisense.ui.input.register

import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.nutrisense.ui.components.AppButton
import com.example.nutrisense.ui.components.InputTextField
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nutrisense.R
import com.example.nutrisense.ui.components.InputPassword
import com.example.nutrisense.ui.theme.NutriSenseTheme

// RegisterRoute Composable adalah entry point untuk layar registrasi
@Composable
internal fun RegisterRoute(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
    navController: NavController,
    sharedPreferences: SharedPreferences // Pass SharedPreferences for saving the user status
) {
    val errorMessages by viewModel.errorMessages.collectAsState()
    val registerUiInfo by viewModel.registerUiInfo.collectAsState()
    val passwordVisible by viewModel.passwordVisible.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()

    // Call RegisterScreen with navController and sharedPreferences
    RegisterScreen(
        modifier = modifier,
        registerUiInfo = registerUiInfo,
        errorMessages = errorMessages,
        register = viewModel::register,
        onPasswordVisibilityChanged = viewModel::onPasswordVisibilityChanged,
        onUsernameChanged = viewModel::onUsernameChanged,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        passwordVisible = passwordVisible,
        loading = loading,
        clearErrorMessages = viewModel::clearErrorMessages,
        successMessage = successMessage,
        navController = navController,
        sharedPreferences = sharedPreferences
    )
}

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    registerUiInfo: RegisterUiInfo,
    errorMessages: String? = null,
    register: () -> Unit = {},
    onPasswordVisibilityChanged: () -> Unit = {},
    onUsernameChanged: (String) -> Unit = {},
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    passwordVisible: Boolean = false,
    loading: Boolean = false,
    clearErrorMessages: () -> Unit = {},
    successMessage: String? = null,
    navController: NavController,
    sharedPreferences: SharedPreferences // Pass SharedPreferences to RegisterScreen
) {
    val context = LocalContext.current

    // Toast for error message
    LaunchedEffect(errorMessages) {
        if (errorMessages != null) {
            Toast.makeText(context, errorMessages, Toast.LENGTH_SHORT).show()
            kotlinx.coroutines.delay(100) // Delay before clearing the message
            clearErrorMessages()
        }
    }

    // Toast for success message
    LaunchedEffect(successMessage) {
        if (successMessage != null) {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
            kotlinx.coroutines.delay(100) // Delay before clearing the message
            clearErrorMessages()

            // Save login status and new user
            sharedPreferences.edit().putBoolean("is_logged_in", true).apply()

            // Navigate to login screen after successful registration
            navController.navigate("login") {
                popUpTo("register") { inclusive = true } // Clear the registration screen from stack
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background // Background color based on theme
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Input Email
            InputTextField(
                text = registerUiInfo.email,
                label = "Email",
                onValueChange = { onEmailChanged(it) }
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Input Username
            InputTextField(
                text = registerUiInfo.username,
                label = "Username",
                onValueChange = { onUsernameChanged(it) }
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Input Password
            InputPassword(
                passwordVisible = passwordVisible,
                value = registerUiInfo.password,
                togglePasswordVisibility = onPasswordVisibilityChanged,
                onValueChange = { onPasswordChanged(it) }
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Register Button
            AppButton(
                text = stringResource(R.string.register),
                enabled = loading.not()
            ) {
                register()
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Login Text
            LoginText(navController = navController) // Pass navController to LoginText
        }
    }
}



@Composable
fun LoginText(navController: NavController) {
    val annotatedString = buildAnnotatedString {
        append("Sudah punya akun? ")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary, // Warna sesuai tema
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Masuk")
        }
    }

    // Teks dapat diklik untuk navigasi
    BasicText(
        text = annotatedString,
        modifier = Modifier.clickable {
            navController.navigate("login") // Navigasi ke halaman login
        },
        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground) // Warna teks sesuai tema
    )
}
