package com.example.nutrisense.ui.input.weight

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.nutrisense.R
import com.example.nutrisense.ui.input.UserInputViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightInputScreen(
    userInputViewModel: UserInputViewModel = viewModel(),
    navController: NavController
) {
    // Mengambil data berat dari ViewModel dan menggunakan collectAsState() untuk mendapatkan nilai terkini
    val userData by userInputViewModel.userData.collectAsState()
    val weight = userData.weight?.toString() ?: "0" // Mengambil nilai berat atau default "0"

    // Load Lottie animation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.weight))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    // Get the theme's color scheme
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Bagian atas
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "What’s Your Weight?",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.primary,
                    letterSpacing = 1.5.sp
                ),
                modifier = Modifier.padding(top = 46.dp)
            )

            // Lottie Animation
            Box(
                modifier = Modifier
                    .size(200.dp)
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = progress
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Weight Input with "kg" inside the input field
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = weight,
                    onValueChange = { newValue ->
                        // Only allow valid integer input
                        if (newValue.all { it.isDigit() }) {
                            // Update the weight value in ViewModel
                            userInputViewModel.updateWeight(newValue.toInt(),
                                onSuccess = {
                                    Log.d("WeightInputScreen", "Weight successfully updated")
                                },
                                onError = { errorMessage ->
                                    Log.e("WeightInputScreen", "Error updating weight: $errorMessage")
                                }
                            )
                            Log.d("WeightInputScreen", "Weight updated to: $newValue")
                        }
                    },
                    singleLine = true,
                    label = { Text("Enter your weight") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Text(
                    text = "Kg",
                    style = MaterialTheme.typography.bodyMedium.copy(color = colorScheme.onBackground),
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Bagian bawah
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Next Button
            Button(
                onClick = {
                    // Pastikan data disimpan di ViewModel
                    userInputViewModel.updateWeight(weight.toIntOrNull() ?: 0,
                        onSuccess = {
                            Log.d("WeightInputScreen", "Weight successfully updated on Next button")
                        },
                        onError = { errorMessage ->
                            Log.e("WeightInputScreen", "Error on Next button: $errorMessage")
                        }
                    )
                    // Navigasi ke halaman berikutnya setelah data disimpan di ViewModel
                    navController.navigate("height_input")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorScheme.primary)
            ) {
                Text(
                    text = "Next",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Footer Text
            Text(
                text = "By continuing, you agree to NutriSense\nPrivacy Policy and Terms of Use",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall.copy(color = colorScheme.onBackground),
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
        }
    }
}









