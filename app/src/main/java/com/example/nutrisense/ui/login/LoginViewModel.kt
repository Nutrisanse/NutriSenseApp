package com.example.nutrisense.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.example.nutrisense.data.repositories.UserRepository
import com.example.nutrisense.data.resource.response.error.LoginError

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

    var passwordVisible = mutableStateOf(false)
    var loading = mutableStateOf(false)
    var successMessage = mutableStateOf<String?>(null)
    fun login() {

        viewModelScope.launch {
            loading.value = true
            try {
                val response = userRepository.loginUser(
                    loginUiInfo.value.email,
                    loginUiInfo.value.password
                )
                val jwt = JWT(response.accessToken)
                userRepository.saveAccessToken(
                    response.accessToken,
                    jwt.getClaim("name").asString() ?: ""
                )
                withContext(Dispatchers.Main) {
                    successMessage.value = "Login Success"
                }
            } catch (e: HttpException) {

                if (e.code() == 401) {
                    errorMessages.value = "Email atau password salah"
                    return@launch
                }

                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, LoginError::class.java)
                errorMessages.value = errorBody.detail?.get(0)?.msg

            } catch (e: Exception) {
                errorMessages.value = "An error occurred"
                Log.d("LoginViewModel", "login: ${e.message}")
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