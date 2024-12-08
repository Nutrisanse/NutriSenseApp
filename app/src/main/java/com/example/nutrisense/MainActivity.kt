package com.example.nutrisense

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutrisense.ui.WelcomeScreen
import com.example.nutrisense.ui.input.*
import com.example.nutrisense.ui.homepage.HomeScreen
import com.example.nutrisense.ui.profile.ProfileScreen
import com.example.nutrisense.ui.theme.NutriSenseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriSenseTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") {
                        WelcomeScreen(
                            onContinueClick = {
                                navController.navigate("sex_selection")
                            }
                        )
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
                            onScanFoodClick = {
                                try {
                                    val intent = Intent("android.media.action.IMAGE_CAPTURE")
                                    startActivity(intent)
                                } catch (e: ActivityNotFoundException) {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Camera not supported on this device.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                    }
                    composable("profile") {
                        ProfileScreen(
                            onCancelClick = { navController.popBackStack() },
                            onSaveClick = {
                                Toast.makeText(this@MainActivity, "Profile Saved", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}
