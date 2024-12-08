package com.example.nutrisense.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutrisense.R

@Composable
fun GreetingCardWithHealthStatus(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .heightIn(min = 390.dp), // Tambahkan minimum tinggi agar lebih panjang
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp) // Sesuaikan jarak antar komponen
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hello, User!",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = "Nice to have you back",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .clickable {
                            navController.navigate("profile")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = "Profile",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(32.dp) // Perbesar ukuran ikon
                    )
                }
            }

            Text(
                text = "Your Health Status",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            // Menambahkan HealthStatusBox untuk BMI dan Alergi hanya menampilkan teks dalam kotak
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp), // Sesuaikan jarak antar HealthStatusBox
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusBoxTextOnly(
                    status = "BMI",
                    value = "24.5 kg/m²",
                    modifier = Modifier.weight(1f)
                )
                StatusBoxTextOnly(
                    status = "Alergi",
                    value = "Peanuts",
                    modifier = Modifier.weight(1f)
                )
            }

            // Menambahkan HealthStatusBox dalam satu baris secara horizontal untuk status lainnya
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp), // Sesuaikan jarak antar HealthStatusBox
                verticalAlignment = Alignment.CenterVertically
            ) {
                HealthStatusBox(
                    status = "Blood Pressure",
                    value = "120/80",
                    progress = 1f,
                    modifier = Modifier.weight(1f) // Memberikan bobot agar kotak saling berbagi ruang
                )
                HealthStatusBox(
                    status = "Sugar Level",
                    value = "90 mg/dL",
                    progress = 0.9f,
                    modifier = Modifier.weight(1f) // Memberikan bobot agar kotak saling berbagi ruang
                )
                HealthStatusBox(
                    status = "Cholesterol",
                    value = "200 mg/dL",
                    progress = 0.8f,
                    modifier = Modifier.weight(1f) // Memberikan bobot agar kotak saling berbagi ruang
                )
            }
        }
    }
}


@Composable
fun StatusBoxTextOnly(status: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = status,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}
