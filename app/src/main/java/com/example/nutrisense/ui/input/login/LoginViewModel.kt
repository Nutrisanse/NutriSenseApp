package com.example.nutrisense.ui.input.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.example.nutrisense.data.repositories.UserRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val loginUiInfo by lazy {
        MutableStateFlow(
            LoginUiInfo("", "")
        )
    }

    val errorMessages = mutableStateOf<String?>(null)
    val passwordVisible = mutableStateOf(false)
    val loading = mutableStateOf(false)
    val successMessage = mutableStateOf<String?>(null)

    fun login() {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = userRepository.loginUser(
                    loginUiInfo.value.email,
                    loginUiInfo.value.password
                )

                Log.d("LoginViewModel", "Response: $response")

                val token = response.token // Mengakses token dari respons
                if (token != null) {
                    val jwt = JWT(token)
                    val usernameClaim = jwt.getClaim("username").asString()
                    userRepository.saveAccessToken(token, usernameClaim ?: "Unknown")
                    withContext(Dispatchers.Main) {
                        successMessage.value = "Login Success"
                        Log.d("LoginViewModel", "Token: $token")
                    }
                } else {
                    errorMessages.value = "Token is null"
                    Log.e("LoginViewModel", "Token is null")
                    errorMessages.value = "Token tidak ditemukan"
                }
            } catch (e: HttpException) {
                val jsonError = e.response()?.errorBody()?.string()
                Log.e("LoginViewModel", "HttpException: $jsonError")
                errorMessages.value = "Email atau password salah"
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception: ${e.message}")
                errorMessages.value = "An unexpected error occurred"
            } finally {
                loading.value = false
            }
        }
    }

    fun onEmailChanged(email: String) {
        loginUiInfo.value = loginUiInfo.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        loginUiInfo.value = loginUiInfo.value.copy(password = password)
    }

    fun onPasswordVisibilityChanged() {
        passwordVisible.value = !passwordVisible.value
    }

    fun clearErrorMessages() {
        errorMessages.value = null
        successMessage.value = null
    }
}

data class LoginUiInfo(
    val email: String,
    val password: String
)
