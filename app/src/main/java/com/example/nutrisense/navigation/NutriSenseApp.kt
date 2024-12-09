package com.example.nutrisense.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutrisense.ui.input.welcome.WelcomeScreen
import com.example.nutrisense.ui.homepage.HomeScreen
import com.example.nutrisense.ui.input.age.AgeInputScreen
import com.example.nutrisense.ui.input.health.HealthInputScreen
import com.example.nutrisense.ui.input.height.HeightInputScreen
import com.example.nutrisense.ui.input.sex.SexSelectionScreen
import com.example.nutrisense.ui.input.weight.WeightInputScreen
import com.example.nutrisense.ui.profile.ProfileScreen
import com.example.nutrisense.ui.login.LoginRoute
import com.example.nutrisense.ui.input.register.RegisterRoute


@Composable
fun NutriSenseApp(onCameraLaunch: () -> Unit, showToast: (String) -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") { // Start dari "welcome"
        composable("welcome") {
            WelcomeScreen(
                onContinueClick = {
                    navController.navigate("login") // Navigasi ke "login"
                }
            )
        }
        composable("login") {
            LoginRoute(
                navController = navController
            )
        }
        composable("register") {
            RegisterRoute(navController = navController)  // Menambahkan NavController ke RegisterRoute
        }
        composable("sex_selection") {
            SexSelectionScreen(
                onSexSelected = { sex ->
                    navController.navigate("weight_input")
                }
            )
        }
        composable("weight_input") {
            WeightInputScreen(
                onWeightSubmitted = { weight ->
                    navController.navigate("height_input")
                }
            )
        }
        composable("height_input") {
            HeightInputScreen(
                onHeightSubmitted = { height ->
                    navController.navigate("age_input")
                }
            )
        }
        composable("age_input") {
            AgeInputScreen(
                onNextClicked = { age ->
                    navController.navigate("health_input")
                }
            )
        }
        composable("health_input") {
            HealthInputScreen(
                onNextClicked = { healthData ->
                    navController.navigate("home")
                }
            )
        }
        composable("home") {
            HomeScreen(
                navController = navController,
                onScanFoodClick = onCameraLaunch
            )
        }
        composable("profile") {
            ProfileScreen(
                onCancelClick = { navController.popBackStack() },
                onSaveClick = {
                    showToast("Profile Saved")
                }
            )
        }
    }
}


