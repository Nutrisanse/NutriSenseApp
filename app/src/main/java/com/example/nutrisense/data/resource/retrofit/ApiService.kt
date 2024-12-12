package com.example.nutrisense.data.resource.retrofit


import com.example.nutrisense.data.resource.request.RegisterRequest
import com.example.nutrisense.data.resource.response.success.LoginResponse
import com.example.nutrisense.data.resource.response.success.RegisterResponse
import com.example.nutrisense.data.resource.response.success.SexUpdateResponse
import com.example.nutrisense.ui.input.UserDataUpdateRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT


interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @PATCH("user/data")  // Endpoint untuk update data
    suspend fun updateUserData(
        @Header("Authorization") token: String,  // Menggunakan token untuk autentikasi
        @Body userData: UserDataUpdateRequest  // Data yang dikirimkan ke server
    ): Response<Void>

}
