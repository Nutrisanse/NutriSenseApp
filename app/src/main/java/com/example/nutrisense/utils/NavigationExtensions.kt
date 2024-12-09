package com.example.nutrisense.utils

import androidx.navigation.NavController

// Fungsi untuk menavigasi ke Home, sekaligus popUp ke login agar tidak ada stack login
fun NavController.navigateToHome() {
    this.navigate("home") {
        popUpTo("login") { inclusive = true }  // Menghapus layar login setelah berpindah ke home
    }
}

// Fungsi untuk menavigasi ke halaman Register
fun NavController.navigateToRegister() {
    this.navigate("register")
}
