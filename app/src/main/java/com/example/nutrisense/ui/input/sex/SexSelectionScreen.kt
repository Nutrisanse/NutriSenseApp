package com.example.nutrisense.ui.input.sex

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.nutrisense.R
import com.example.nutrisense.ui.theme.NutriSenseTheme
import com.example.nutrisense.ui.input.UserInputViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SexSelectionScreen(viewModel: UserInputViewModel, onSexSelected: () -> Unit) {
    // Load Lottie animation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sex))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever // Loop indefinitely
    )

    // Get color scheme from the theme
    val colorScheme = MaterialTheme.colorScheme

    // Use rememberSaveable to persist the user's selected sex
    var selectedSex by rememberSaveable { mutableStateOf<String?>(null) }

    // Observe the ViewModel data
    val userData by viewModel.userData.collectAsState()

    // Gunakan userData untuk menampilkan jenis kelamin yang dipilih
    val currentSex = userData.gender?.let {
        when (it) {
            1 -> "Male"
            0 -> "Female"
            else -> "Not Selected"
        }
    } ?: "Not Selected"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background) // Background based on theme
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Animation and Text Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .padding(top = 90.dp)
        ) {
            // Title
            Text(
                text = "What’s Your Sex?",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.primary // Use theme color
                ),
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Animation
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

            // Subtitle
            Text(
                text = "Why we ask",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = colorScheme.onBackground // Text color based on theme
            )
        }

        // Display the selected sex information (from ViewModel)
        Text(
            text = "Selected sex: $currentSex",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Buttons Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Sex Selection Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        selectedSex = "Male"
                        viewModel.updateGender("Male",
                            onSuccess = {
                                onSexSelected() // Navigate after selection
                            },
                            onError = { errorMessage ->
                                // Tampilkan pesan error jika gagal
                                Log.e("SexSelectionScreen", errorMessage)
                            }
                        )
                    },
                    modifier = Modifier
                        .size(120.dp, 50.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorScheme.primary) // Theme color for buttons
                ) {
                    Text(
                        text = "Male",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onPrimary // Text color based on theme
                    )
                }

                Button(
                    onClick = {
                        selectedSex = "Female"
                        viewModel.updateGender("Female",
                            onSuccess = {
                                onSexSelected() // Navigate after selection
                            },
                            onError = { errorMessage ->
                                // Tampilkan pesan error jika gagal
                                Log.e("SexSelectionScreen", errorMessage)
                            }
                        )
                    },
                    modifier = Modifier
                        .size(120.dp, 50.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorScheme.primary) // Theme color for buttons
                ) {
                    Text(
                        text = "Female",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onPrimary // Text color based on theme
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer Text
            Text(
                text = "By continuing, you agree to NutriSense\nPrivacy Policy and Terms of Use",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = colorScheme.onBackground, // Text color based on theme
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}






