package com.example.nutrisense.ui.input.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nutrisense.R
import com.example.nutrisense.ui.components.AppButton
import com.example.nutrisense.ui.components.InputPassword
import com.example.nutrisense.ui.components.InputTextField
import com.example.nutrisense.ui.theme.NutriSenseTheme
import com.example.nutrisense.utils.navigateToHome
import com.example.nutrisense.utils.navigateToRegister

@Composable
internal fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val errorMessages = viewModel.errorMessages.value
    val loginUiInfo = viewModel.loginUiInfo.collectAsStateWithLifecycle().value
    LoginScreen(
        modifier,
        loginUiInfo,
        errorMessages,
        viewModel::login,
        viewModel::onPasswordVisibilityChanged,
        viewModel.passwordVisible.value,
        viewModel::onEmailChanged,
        viewModel::onPasswordChanged,
        viewModel.loading.value,
        viewModel::clearErrorMessages,
        viewModel.successMessage.value,
        navController = navController
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginUiInfo: LoginUiInfo,
    errorMessages: String? = null,
    login: () -> Unit = {},
    onPasswordVisibilityChanged: () -> Unit = {},
    passwordVisible: Boolean = false,
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    loading: Boolean = false,
    clearErrorMessages: () -> Unit = {},
    successMessage: String? = null,
    navController: NavController
) {
    val context = LocalContext.current

    // Show error messages in Toast
    LaunchedEffect(successMessage) {
        if (successMessage != null) {
            Log.d("LoginScreen", "Login berhasil: $successMessage")
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
            clearErrorMessages()
            navController.navigate("sex_selection") // Navigate to sex_selection after login success
        }
    }

    LaunchedEffect(errorMessages) {
        if (errorMessages != null) {
            Log.d("LoginScreen", "Error: $errorMessages")
            Toast.makeText(context, errorMessages, Toast.LENGTH_SHORT).show()
            clearErrorMessages()
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background // Warna latar belakang sesuai tema
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Email Input
            InputTextField(
                text = loginUiInfo.email,
                label = "Email",
                onValueChange = { onEmailChanged(it) }
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Password Input
            InputPassword(
                passwordVisible = passwordVisible,
                value = loginUiInfo.password,
                togglePasswordVisibility = onPasswordVisibilityChanged,
                onValueChange = { onPasswordChanged(it) }
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Login Button
            AppButton(
                text = stringResource(R.string.login),
                enabled = !loading
            ) {
                login()
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Register Text
            RegisterText(onRegisterClick = {
                navController.navigate("register") // Navigate to register screen
            })
        }
    }
}


@Composable
fun RegisterText(onRegisterClick: () -> Unit) {
    Text(
        text = buildAnnotatedString {
            append("Belum punya akun? ")
            pushStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            )
            append("Daftar")
            pop()
        },
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRegisterClick() }
            .padding(vertical = 8.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    NutriSenseTheme {
        LoginScreen(
            loginUiInfo = LoginUiInfo(email = "", password = ""),
            navController = navController
        )
    }
}
