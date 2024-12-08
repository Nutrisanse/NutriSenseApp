package com.example.nutrisense.ui.input.weight

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.nutrisense.R
import com.example.nutrisense.ui.theme.NutriSenseTheme

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun WeightInputScreen(
    onWeightSubmitted: (String) -> Unit,
    viewModel: WeightInputViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    // Observe weight state dari ViewModel
    val weight by viewModel.weight.collectAsState()

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
                        viewModel.updateWeight(newValue) // Update berat badan melalui ViewModel
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
                    onWeightSubmitted(weight) // Kirim berat badan ke callback
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

// Preview Section
@Preview(showBackground = true)
@Composable
fun WeightInputScreenPreview() {
    NutriSenseTheme {
        WeightInputScreen(onWeightSubmitted = {})
    }
}

