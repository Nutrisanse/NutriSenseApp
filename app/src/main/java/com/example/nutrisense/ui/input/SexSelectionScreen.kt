package com.example.nutrisense.ui.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@Composable
fun SexSelectionScreen(onSexSelected: (String) -> Unit) {
    // Load Lottie animation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sex))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever // Loop indefinitely
    )

    // Get color scheme from the theme
    val colorScheme = MaterialTheme.colorScheme

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
                    onClick = { onSexSelected("Male") },
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
                    onClick = { onSexSelected("Female") },
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

// Preview Section
@Preview(showBackground = true)
@Composable
fun SexSelectionScreenPreview() {
    NutriSenseTheme {
        SexSelectionScreen(onSexSelected = {})
    }
}
