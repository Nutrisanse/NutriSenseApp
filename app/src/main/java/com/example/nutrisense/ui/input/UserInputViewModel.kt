package com.example.nutrisense.ui.input

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrisense.data.repositories.UserRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Data class untuk menyimpan data pengguna
data class UserDataUpdateRequest(
    val gender: Int? = null, // 1 for Male, 0 for Female, menggantikan "sex"
    val weight: Int? = null,
    val height: Int? = null,
    val age: Int? = null,
    val blood_pressure: Int? = null, // menggantikan "bloodPressure"
    val cholesterol: Int? = null,  // Cholesterol level in mg/dL
    val blood_sugar: Int? = null,  // menggantikan "sugarLevel"
    val allergies: String? = null // Still a string
)


@HiltViewModel
class UserInputViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val gson = Gson()

    private val _userData = MutableStateFlow(
        savedStateHandle.get<String>("userData")?.let {
            Log.d("UserInputViewModel", "Retrieved data from SavedStateHandle: $it")
            gson.fromJson(it, UserDataUpdateRequest::class.java)
        } ?: UserDataUpdateRequest()
    )
    val userData: StateFlow<UserDataUpdateRequest> = _userData

    fun saveUserData() {
        Log.d("UserInputViewModel", "Saving data to SavedStateHandle: ${_userData.value}")
        savedStateHandle["userData"] = gson.toJson(_userData.value)
    }

    // Fungsi untuk memperbarui data pengguna
    fun updateGender(gender: String?, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d("UserInputViewModel", "Updating gender with value: $gender")

        val mappedGender = when (gender?.lowercase()) {
            "male" -> 1
            "female" -> 0
            else -> null
        }

        if (mappedGender != null) {
            Log.d("UserInputViewModel", "Mapped gender value: $mappedGender")

            viewModelScope.launch {
                try {
                    val currentUserData = _userData.value.copy(gender = mappedGender)
                    val token = userRepository.getAccessToken()
                    Log.d("UserInputViewModel", "Sending data to update gender: $currentUserData")

                    val result = userRepository.updateUserData(token, currentUserData)

                    if (result.isSuccessful) {
                        Log.d("UserInputViewModel", "Successfully updated gender on server: $mappedGender")
                        onSuccess()
                    } else {
                        val errorMessage = "Error: ${result.message()}"
                        Log.e("UserInputViewModel", "Server error: $errorMessage")
                        onError(errorMessage)
                    }
                } catch (e: Exception) {
                    val errorMessage = e.localizedMessage ?: "Unknown error occurred"
                    Log.e("UserInputViewModel", "Exception occurred while updating gender: $errorMessage", e)
                    onError(errorMessage)
                }
            }
        } else {
            Log.e("UserInputViewModel", "Invalid gender value: $gender")
            onError("Invalid gender value provided.")
        }
    }

    fun updateWeight(weight: Int?, onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (weight != null && weight > 0) {
            Log.d("UserInputViewModel", "Updating weight: $weight")

            // Memperbarui nilai di StateFlow
            _userData.value = _userData.value.copy(weight = weight)

            // Menyimpan data ke SavedStateHandle
            saveUserData()

            // Mengirim data ke server
            viewModelScope.launch {
                try {
                    val currentUserData = _userData.value
                    val token = userRepository.getAccessToken()
                    val result = userRepository.updateUserData(token, currentUserData)

                    if (result.isSuccessful) {
                        Log.d("UserInputViewModel", "Successfully updated weight on server: $weight")
                        onSuccess()
                    } else {
                        val errorMessage = "Error: ${result.message()}"
                        Log.e("UserInputViewModel", "Server error: $errorMessage")
                        onError(errorMessage)
                    }
                } catch (e: Exception) {
                    val errorMessage = e.localizedMessage ?: "Unknown error occurred"
                    Log.e("UserInputViewModel", "Exception occurred while updating weight: $errorMessage", e)
                    onError(errorMessage)
                }
            }

            Log.d("UserInputViewModel", "Updated Weight: $weight, Current Data: ${_userData.value}")
        } else {
            Log.e("UserInputViewModel", "Invalid weight value: $weight")
        }
    }

    // Fungsi untuk memperbarui data tinggi badan
    fun updateHeight(height: Int?, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        // Pastikan height valid dan berbeda dengan nilai saat ini
        if (height != null && height > 0 && height != _userData.value.height) {
            Log.d("UserInputViewModel", "Updating height: $height")

            // Memperbarui nilai di StateFlow
            _userData.value = _userData.value.copy(height = height)

            // Menyimpan data ke SavedStateHandle
            saveUserData()

            // Mengirim data ke server
            viewModelScope.launch {
                try {
                    val currentUserData = _userData.value
                    val token = userRepository.getAccessToken()

                    if (token.isNullOrEmpty()) {
                        onError(Exception("Invalid access token"))
                        return@launch
                    }

                    val result = userRepository.updateUserData(token, currentUserData)

                    if (result.isSuccessful) {
                        Log.d("UserInputViewModel", "Successfully updated height on server: $height")
                        onSuccess()
                    } else {
                        val errorMessage = "Error: ${result.message()}"
                        Log.e("UserInputViewModel", "Server error: $errorMessage")
                        onError(Exception(errorMessage))  // Error sebagai Exception
                    }
                } catch (e: Exception) {
                    Log.e("UserInputViewModel", "Exception occurred while updating height: ${e.localizedMessage}", e)
                    onError(e)  // Menyebarkan Exception ke onError
                }
            }

            Log.d("UserInputViewModel", "Updated Height: $height, Current Data: ${_userData.value}")
        } else {
            Log.d("UserInputViewModel", "No change in height or invalid value: $height")
        }
    }




    // Fungsi untuk memperbarui data usia
    fun updateAge(age: Int?, onSuccess: () -> Unit, onError: (String) -> Unit) {
        // Pastikan usia valid
        if (age != null && age > 0) {
            // Ambil nilai usia saat ini dari StateFlow
            val currentAge = _userData.value.age

            // Cek apakah usia yang baru sama dengan usia saat ini
            if (currentAge == age) {
                Log.d("UserInputViewModel", "Age is the same, no need to update.")
                return  // Tidak ada perubahan, jadi tidak perlu melakukan update
            }

            Log.d("UserInputViewModel", "Updating age: $age")

            // Memperbarui nilai di StateFlow
            _userData.value = _userData.value.copy(age = age)

            // Menyimpan data ke SavedStateHandle
            saveUserData()

            // Mengirim data ke server
            viewModelScope.launch {
                try {
                    val currentUserData = _userData.value
                    val token = userRepository.getAccessToken()

                    if (token.isNullOrEmpty()) {
                        onError("Invalid access token")
                        return@launch
                    }

                    val result = userRepository.updateUserData(token, currentUserData)

                    if (result.isSuccessful) {
                        Log.d("UserInputViewModel", "Successfully updated age on server: $age")
                        onSuccess()
                    } else {
                        val errorMessage = "Error: ${result.message()}"
                        Log.e("UserInputViewModel", "Server error: $errorMessage")
                        onError(errorMessage)
                    }
                } catch (e: Exception) {
                    val errorMessage = e.localizedMessage ?: "Unknown error occurred"
                    Log.e("UserInputViewModel", "Exception occurred while updating age: $errorMessage", e)
                    onError(errorMessage)
                }
            }

            Log.d("UserInputViewModel", "Updated Age: $age, Current Data: ${_userData.value}")
        } else {
            Log.e("UserInputViewModel", "Invalid age value: $age")
            onError("Invalid age value provided.")
        }
    }


    // Fungsi untuk memperbarui data kesehatan lainnya
    fun updateHealthData(
        blood_pressure: Int?,
        cholesterol: Int?,
        blood_sugar: Int?,
        allergies: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // Memperbarui nilai di StateFlow
        _userData.value = _userData.value.copy(
            blood_pressure = blood_pressure,
            cholesterol = cholesterol,
            blood_sugar = blood_sugar,
            allergies = allergies
        )

        // Menyimpan data ke SavedStateHandle
        saveUserData()

        // Mengirim data ke server
        viewModelScope.launch {
            try {
                val currentUserData = _userData.value
                val token = userRepository.getAccessToken()
                val result = userRepository.updateUserData(token, currentUserData)

                if (result.isSuccessful) {
                    Log.d("UserInputViewModel", "Successfully updated health data on server.")
                    onSuccess()
                } else {
                    val errorMessage = "Error: ${result.message()}"
                    Log.e("UserInputViewModel", "Server error: $errorMessage")
                    onError(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = e.localizedMessage ?: "Unknown error occurred"
                Log.e("UserInputViewModel", "Exception occurred while updating health data: $errorMessage", e)
                onError(errorMessage)
            }
        }

        Log.d("UserInputViewModel", "Updated Health Data: BP=$blood_pressure, Chol=$cholesterol, Sugar=$blood_sugar, Allergies=$allergies, Current Data: ${_userData.value}")
    }
}




