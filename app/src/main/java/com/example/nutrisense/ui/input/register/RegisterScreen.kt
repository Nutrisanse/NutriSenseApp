package com.example.nutrisense.ui.input.register

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.nutrisense.ui.components.AppButton
import com.example.nutrisense.ui.components.InputTextField
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    navController: NavController  // Terima NavController sebagai parameter
) {
    val errorMessages = viewModel.errorMessages.value
    val registerUiInfo = viewModel.registerUiInfo.collectAsStateWithLifecycle().value

    // Panggil RegisterScreen dengan navController untuk navigasi
    RegisterScreen(
        modifier = modifier,
        registerUiInfo = registerUiInfo,
        errorMessages = errorMessages,
        register = viewModel::register,
        onPasswordVisibilityChanged = viewModel::onPasswordVisibilityChanged,
        onNameChanged = viewModel::onNameChanged,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        passwordVisible = viewModel.passwordVisible.value,
        loading = viewModel.loading.value,
        clearErrorMessages = viewModel::clearErrorMessages,
        successMessage = viewModel.successMessage.value,
        navController = navController // Menyertakan navController ke RegisterScreen
    )
}

// RegisterScreen adalah UI yang merender form pendaftaran
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    registerUiInfo: RegisterUiInfo,
    errorMessages: String? = null,
    register: () -> Unit = {},
    onPasswordVisibilityChanged: () -> Unit = {},
    onNameChanged: (String) -> Unit = {},
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    passwordVisible: Boolean = false,
    loading: Boolean = false,
    clearErrorMessages: () -> Unit = {},
    successMessage: String? = null,
    navController: NavController
) {
    val context = LocalContext.current

    // Toast untuk pesan error
    LaunchedEffect(errorMessages) {
        if (errorMessages != null) {
            Toast.makeText(context, errorMessages, Toast.LENGTH_SHORT).show()
            clearErrorMessages()
        }
    }

    // Toast untuk pesan sukses
    LaunchedEffect(successMessage) {
        if (successMessage != null) {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
            clearErrorMessages()
        }
    }

    // Menggunakan Surface untuk memastikan warna latar belakang sesuai tema
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background // Warna latar belakang berdasarkan tema
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

            // Input Nama
            InputTextField(
                text = registerUiInfo.name,
                label = "Nama",
                onValueChange = { onNameChanged(it) }
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

            // Tombol Register
            AppButton(
                text = stringResource(R.string.register),
                enabled = loading.not()
            ) {
                register()
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Teks Login
            LoginText(navController = navController) // Pass navController ke LoginText
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

// Preview untuk melihat tampilan RegisterScreen di Android Studio
@Preview(showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    NutriSenseTheme {
        val navController = rememberNavController()  // Membuat NavController di dalam preview
        RegisterScreen(
            registerUiInfo = RegisterUiInfo(
                email = "",
                password = "",
                name = ""
            ),
            navController = navController  // Mengirimkan NavController ke RegisterScreen
        )
    }
}

