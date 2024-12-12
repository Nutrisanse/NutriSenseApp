package com.example.nutrisense.data.resource.response.success

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("token")
    val token: String?, // Gunakan nullable karena token bisa saja null

    @field:SerializedName("userId")
    val userId: String?, // Tambahkan userId jika diperlukan

    @field:SerializedName("data")
    val data: UserData? // Data tambahan seperti email dan username
)

data class UserData(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String
)
