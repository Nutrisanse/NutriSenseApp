package com.example.nutrisense.data.resource.response.success

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: String
)