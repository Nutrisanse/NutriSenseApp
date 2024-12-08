package com.example.nutrisense.ui.input

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.nutrisense.R
import com.example.nutrisense.ui.theme.NutriSenseTheme

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HealthInputScreen(onNextClicked: (String) -> Unit) {
    var bloodPressure by remember { mutableStateOf("") }
    var cholesterol by remember { mutableStateOf("") }
    var sugarLevel by remember { mutableStateOf("") }
    var allergies by remember { mutableStateOf("") }

    // Load Lottie animation for health
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.health)) // Use health.json file
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever // Loop indefinitely
    )

    NutriSenseTheme(darkTheme = isSystemInDarkTheme()) { // Use the current system theme
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background), // Set dynamic background color
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Column atas: Text, Animasi, dan Input Fields
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        // Title
                        Text(
                            text = "Enter Your Health Details",
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary, // Dynamic primary color
                                letterSpacing = 1.5.sp
                            )
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

                        // Input Fields
                        OutlinedTextField(
                            value = bloodPressure,
                            onValueChange = { bloodPressure = it },
                            label = { Text("Blood Pressure") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                Text(
                                    text = "mmHg",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(

                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        )

                        OutlinedTextField(
                            value = cholesterol,
                            onValueChange = { cholesterol = it },
                            label = { Text("Cholesterol") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                Text(
                                    text = "mg/dL",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(

                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        )

                        OutlinedTextField(
                            value = sugarLevel,
                            onValueChange = { sugarLevel = it },
                            label = { Text("Sugar Level") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                Text(
                                    text = "mg/dL",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(

                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        )

                        OutlinedTextField(
                            value = allergies,
                            onValueChange = { allergies = it },
                            label = { Text("Allergies") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(

                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        )
                        Text(
                            text = "You can fill it later if you're not sure about some details.",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), // Slightly faded text
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    // Column bawah: Tombol Next dan Footer
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Next Button
                        Button(
                            onClick = {
                                val healthData =
                                    "Blood Pressure: $bloodPressure, Cholesterol: $cholesterol, Sugar Level: $sugarLevel, Allergies: $allergies"
                                onNextClicked(healthData)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary) // Dynamic button color
                        ) {
                            Text(
                                text = "Next",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary // Dynamic text color for button
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        // Footer Text
                        Text(
                            text = "By continuing, you agree to NutriSense\nPrivacy Policy and Terms of Use",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface, // Dynamic text color
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HealthInputScreenPreview() {
    NutriSenseTheme {
        HealthInputScreen(onNextClicked = {})
    }
}
