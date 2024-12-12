package com.example.nutrisense.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.nutrisense.ui.input.login.LoginRoute
import com.example.nutrisense.ui.input.register.RegisterRoute
import com.example.nutrisense.ui.input.UserInputViewModel

@Composable
fun NutriSenseApp(onCameraLaunch: () -> Unit, showToast: (String) -> Unit) {
    val navController = rememberNavController()

    // SharedPreferences for saving login status and first-time usage status
    val sharedPreferences = LocalContext.current.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

    NavHost(navController = navController, startDestination = if (isLoggedIn) "home" else "welcome") {
        composable("welcome") {
            WelcomeScreen(
                onContinueClick = {
                    navController.navigate("login")
                }
            )
        }

        composable("login") {
            LoginRoute(
                navController = navController,
                sharedPreferences = sharedPreferences,
                onLoginSuccess = {
                    val isFirstTime = sharedPreferences.getBoolean("is_first_time", true)
                    if (isFirstTime) {
                        navController.navigate("sex_selection") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }

        composable("sex_selection") {
            val userInputViewModel: UserInputViewModel = hiltViewModel()
            SexSelectionScreen(
                viewModel = userInputViewModel,
                onSexSelected = {
                    navController.navigate("weight_input")
                }
            )
        }

        composable("register") {
            RegisterRoute(
                navController = navController,
                sharedPreferences = sharedPreferences // Pass SharedPreferences
            )
        }

        composable("weight_input") {
            val userInputViewModel: UserInputViewModel = hiltViewModel()
            WeightInputScreen(
                userInputViewModel = userInputViewModel,
                navController = navController
            )
        }

        composable("height_input") {
            val userInputViewModel: UserInputViewModel = hiltViewModel()
            HeightInputScreen(
                userInputViewModel = userInputViewModel,
                navController = navController
            )
        }

        composable("age_input") {
            val userInputViewModel: UserInputViewModel = hiltViewModel()
            AgeInputScreen(
                userInputViewModel = userInputViewModel,
                navController = navController
            )
        }

        composable("health_input") {
            val userInputViewModel: UserInputViewModel = hiltViewModel()
            HealthInputScreen(
                navController = navController,
                userInputViewModel = userInputViewModel
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

