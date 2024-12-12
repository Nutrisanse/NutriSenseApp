package com.example.nutrisense.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(
                onContinueClick = {
                    navController.navigate("login")
                }
            )
        }
        composable("login") {
            LoginRoute(navController = navController)
        }
        composable("sex_selection") {
            val navBackStackEntry = remember { navController.currentBackStackEntry }
            val userInputViewModel: UserInputViewModel = hiltViewModel(navBackStackEntry!!)
            SexSelectionScreen(
                viewModel = userInputViewModel,
                onSexSelected = {
                    navController.navigate("weight_input")
                }
            )
        }
        composable("register") {
            RegisterRoute(navController = navController)
        }
        composable("weight_input") {
            val navBackStackEntry = remember { navController.currentBackStackEntry }
            val userInputViewModel: UserInputViewModel = hiltViewModel(navBackStackEntry!!)
            WeightInputScreen(
                userInputViewModel = userInputViewModel,
                navController = navController
            )
        }
        composable("height_input") {
            val navBackStackEntry = remember { navController.currentBackStackEntry }
            val userInputViewModel: UserInputViewModel = hiltViewModel(navBackStackEntry!!)
            HeightInputScreen(
                userInputViewModel = userInputViewModel,
                navController = navController
            )
        }
        composable("age_input") {
            val navBackStackEntry = remember { navController.currentBackStackEntry }
            val userInputViewModel: UserInputViewModel = hiltViewModel(navBackStackEntry!!)
            AgeInputScreen(
                userInputViewModel = userInputViewModel,
                navController = navController
            )
        }
        composable("health_input") {
            val navBackStackEntry = remember { navController.currentBackStackEntry }
            val userInputViewModel: UserInputViewModel = hiltViewModel(navBackStackEntry!!)
            HealthInputScreen(
                navController = navController,
                userInputViewModel = userInputViewModel,
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




