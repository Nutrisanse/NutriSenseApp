package com.example.nutrisense.ui.input.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrisense.data.repositories.UserRepository
import com.example.nutrisense.data.resource.response.error.RegisterError
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val registerUiInfo by lazy {
        MutableStateFlow(
            RegisterUiInfo("", "", "")
        )
    }

    val errorMessages = mutableStateOf<String?>(null)

    var passwordVisible = mutableStateOf(false)
    var loading = mutableStateOf(false)
    var successMessage = mutableStateOf<String?>(null)
    fun register() {

        viewModelScope.launch {
            loading.value = true
            try {
                val response = userRepository.registerUser(
                    registerUiInfo.value.name,
                    registerUiInfo.value.email,
                    registerUiInfo.value.password
                )
                successMessage.value = response.message
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, RegisterError::class.java)
                Log.d("RegisterViewModel", "register: ${errorBody.message}")
                errorMessages.value = errorBody.message
            } catch (e: Exception) {
                errorMessages.value = "An error occurred"
                Log.d("RegisterViewModel", "register: ${e.message}")
            } finally {
                loading.value = false
            }
        }
    }

    fun onNameChanged(name: String) {
        registerUiInfo.value = registerUiInfo.value.copy(name = name)
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

}

data class RegisterUiInfo(
    val name: String,
    val email: String,
    val password: String
)