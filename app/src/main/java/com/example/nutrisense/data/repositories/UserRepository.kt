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

    suspend fun registerUser(name: String, email: String, password: String): RegisterResponse {
        val request = RegisterRequest(email, name, password)

        return apiService.register(request)

    }

    suspend fun loginUser(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }


    suspend fun saveAccessToken(token: String, userName: String) {

        userPreferenceStore.saveAccessToken(token, userName)
    }

}