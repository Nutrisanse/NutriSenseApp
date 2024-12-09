package com.example.nutrisense.data.repositories

import com.example.nutrisense.data.resource.request.RegisterRequest
import com.example.nutrisense.data.resource.response.success.LoginResponse
import com.example.nutrisense.data.resource.response.success.RegisterResponse
import com.example.nutrisense.data.resource.retrofit.ApiService
import com.example.nutrisense.data.storage.UserPreferenceStore
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreferenceStore: UserPreferenceStore
) {

    // Fungsi untuk melakukan registrasi user
    suspend fun registerUser(name: String, email: String, password: String): RegisterResponse {
        // Membuat request untuk registrasi
        val request = RegisterRequest(email, name, password)

        // Melakukan request ke API
        return apiService.register(request)
    }

    // Fungsi untuk melakukan login user
    suspend fun loginUser(email: String, password: String): LoginResponse {
        // Melakukan request login ke API
        return apiService.login(email, password)
    }

    // Fungsi untuk menyimpan token dan username ke SharedPreferences menggunakan UserPreferenceStore
    suspend fun saveAccessToken(token: String, userName: String) {
        userPreferenceStore.saveAccessToken(token, userName)
    }

    // Fungsi untuk mendapatkan token yang disimpan
    suspend fun getAccessToken(): String {
        return userPreferenceStore.getAccessToken()
    }

    // Fungsi untuk mendapatkan username yang disimpan
    suspend fun getUserName(): String {
        return userPreferenceStore.getUserName()
    }

    // Fungsi untuk logout user, menghapus data yang disimpan
    suspend fun logoutUser() {
        userPreferenceStore.clearUserData()
    }
}
