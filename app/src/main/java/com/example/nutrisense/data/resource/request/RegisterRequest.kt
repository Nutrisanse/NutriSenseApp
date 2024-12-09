package com.example.nutrisense.data.resource.request

data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String
)