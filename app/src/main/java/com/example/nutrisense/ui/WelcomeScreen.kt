package com.example.nutrisense.ui

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
fun WelcomeScreen(onContinueClick: () -> Unit) {
    // Lottie animation state
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.welcome))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever // Loop the animation indefinitely
    )

    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background) // Set latar belakang sesuai tema
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bagian atas (kosong untuk memberi jarak)
        Spacer(modifier = Modifier.height(16.dp))

        // Bagian tengah (animasi, teks welcome, dan deskripsi)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Animasi
            Box(
                modifier = Modifier
                    .size(200.dp) // Ukuran animasi
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = progress  // Animasi berjalan terus
                )
            }

            // Teks Welcome
            Text(
                text = "Welcome to NutriSense",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                color = colorScheme.primary, // Warna dari tema
                modifier = Modifier.padding(top = 16.dp) // Jarak setelah animasi
            )

            // Deskripsi singkat
            Text(
                text = "Track and improve your nutrition with ease!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    color = colorScheme.onBackground // Menggunakan warna teks tema
                ),
                modifier = Modifier.padding(top = 8.dp) // Jarak kecil setelah teks welcome
            )
        }

        // Bagian bawah (tombol dan teks footer)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Tombol Continue
            Button(
                onClick = onContinueClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorScheme.primary) // Warna tombol dari tema
            ) {
                Text(
                    text = "Continue",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onPrimary // Warna teks tombol dari tema
                )
            }

            // Teks Footer
            Text(
                text = "By continuing, you agree to NutriSense\nPrivacy Policy and Terms of Use",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = colorScheme.onBackground, // Warna dari tema
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp) // Jarak bawah layar
            )
        }
    }
}

// Preview Section
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    NutriSenseTheme {
        WelcomeScreen(onContinueClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenDarkPreview() {
    NutriSenseTheme(darkTheme = true) {
        WelcomeScreen(onContinueClick = {})
    }
}



