package com.example.nutrisense.data.resource.response.success

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val user: String
)