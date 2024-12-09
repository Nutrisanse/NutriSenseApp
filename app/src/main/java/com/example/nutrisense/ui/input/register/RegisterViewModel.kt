package com.example.nutrisense.ui.input.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrisense.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val registerUiInfo = MutableStateFlow(RegisterUiInfo("", "", ""))
    val errorMessages = MutableStateFlow<String?>(null)
    val passwordVisible = MutableStateFlow(false)
    val loading = MutableStateFlow(false)
    val successMessage = MutableStateFlow<String?>(null)
    val userInfo = MutableStateFlow<String?>(null)

    fun register() {
        if (!isInputValid()) return

        viewModelScope.launch {
            loading.value = true
            try {
                Log.d("RegisterViewModel", "Calling API at: https://backend-service-1057600249204.us-central1.run.app/register")
                Log.d("RegisterViewModel", "Attempting to register user with:")
                Log.d("RegisterViewModel", "Username: ${registerUiInfo.value.username}")
                Log.d("RegisterViewModel", "Email: ${registerUiInfo.value.email}")
                Log.d("RegisterViewModel", "Password: ${registerUiInfo.value.password}")
                val response = userRepository.registerUser(
                    registerUiInfo.value.email,
                    registerUiInfo.value.password,
                    registerUiInfo.value.username
                )
                Log.d("RegisterViewModel", "Registration successful: ${response.message}")
                successMessage.value = response.message
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("RegisterViewModel", "HTTP Error: ${e.code()}, Body: $errorBody")
                errorMessages.value = "HTTP Error: ${e.code()}"
            }  catch (e: Exception) {
                Log.e("RegisterViewModel", "Exception: ${e.message}")
                errorMessages.value = "An error occurred: ${e.message}"
            }
            finally {
                loading.value = false
            }
        }
    }

    fun onUsernameChanged(username: String) {
        registerUiInfo.value = registerUiInfo.value.copy(username = username)
    }

    fun onEmailChanged(email: String) {
        registerUiInfo.value = registerUiInfo.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        registerUiInfo.value = registerUiInfo.value.copy(password = password)
    }

    fun onPasswordVisibilityChanged() {
        passwordVisible.value = !passwordVisible.value
    }

    fun clearErrorMessages() {
        errorMessages.value = null
        successMessage.value = null
    }

    private fun isInputValid(): Boolean {
        val (username, email, password) = registerUiInfo.value
        return when {
            username.isBlank() -> {
                errorMessages.value = "Nama tidak boleh kosong"
                false
            }
            email.isBlank() -> {
                errorMessages.value = "Email tidak boleh kosong"
                false
            }
            password.isBlank() -> {
                errorMessages.value = "Password tidak boleh kosong"
                false
            }
            else -> true
        }
    }
}

data class RegisterUiInfo(
    val email: String,
    val password: String,
    val username: String
)


