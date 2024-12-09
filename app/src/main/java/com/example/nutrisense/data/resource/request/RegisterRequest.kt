package com.example.nutrisense.data.resource.request

data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String
)