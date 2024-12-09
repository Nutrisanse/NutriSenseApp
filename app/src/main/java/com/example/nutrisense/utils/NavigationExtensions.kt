package com.example.nutrisense.utils

import androidx.navigation.NavController

fun NavController.navigateToHome() {
    this.navigate("home") {
        popUpTo("login") { inclusive = true }
    }
}

fun NavController.navigateToRegister() {
    this.navigate("register")
}
