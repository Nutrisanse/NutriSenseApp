package com.example.nutrisense.ui.homepage

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nutrisense.R
import com.example.nutrisense.ui.theme.NutriSenseTheme

@Composable
fun HealthStatusBox(
    status: String,
    value: String,
    progress: Float, // Progress value between 0.0 and 1.0
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(140.dp) // Perbesar ukuran kotak
            .padding(4.dp) // Tambahkan sedikit padding
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)), // Tambahkan bayangan
        shape = RoundedCornerShape(12.dp), // Buat sudut lebih bulat
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Custom Circular Progress with Gradient
            Box(contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.size(80.dp)) {
                    val strokeWidth = 8.dp.toPx()
                    val sweepAngle = progress * 360f

                    // Gradient progress
                    drawArc(
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                Color(0xFF4CAF50), // Hijau untuk awal progress
                                Color(0xFFFFC107), // Kuning untuk progress tengah
                                Color(0xFFF44336)  // Merah untuk akhir progress
                            ),

                        ),
                        startAngle = 5f, // Memulai progress dari atas
                        sweepAngle = sweepAngle, // Total sudut berdasarkan progress
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }

                // Value text inside the circle
                Text(
                    text = value,
                    style = TextStyle(
                        fontSize = 13.sp, // Sedikit lebih besar
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary // Warna lebih menarik
                    )
                )
            }

            // Status Name
            Text(
                text = status,
                style = TextStyle(
                    fontSize = 13.sp, // Sedikit lebih besar
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f) // Lebih halus
                ),
                modifier = Modifier.padding(top = 8.dp) // Tambahkan jarak dengan progress
            )
        }
    }
}


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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.05f) // Perbesar rasio agar lebih panjang
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp), // Kurangi jarak vertikal
                    horizontalArrangement = Arrangement.spacedBy(4.dp) // Kurangi jarak horizontal
                ) {
                    val healthItems = listOf(
                        Triple("BMI", "22.5", 0.75f),
                        Triple("Blood Pressure", "120/80", 0.9f),
                        Triple("Sugar Level", "95 mg/dL", 0.6f),
                        Triple("Heart Rate", "72 bpm", 0.8f),
                        Triple("Cholesterol", "180 mg/dL", 0.7f)
                    )

                    items(healthItems.size) { index ->
                        HealthStatusBox(
                            status = healthItems[index].first,
                            value = healthItems[index].second,
                            progress = healthItems[index].third
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun HomeScreen(navController: NavController, onScanFoodClick: () -> Unit) {
    NutriSenseTheme {
        Scaffold(
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = onScanFoodClick,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_scanner),
                            contentDescription = "Scan Food",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    GreetingCardWithHealthStatus(navController = navController)

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Nutrition for You",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        repeat(3) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color.Gray)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Nasi goreng ayam",
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        )
                                        Text(
                                            text = "komposisi 1, komposisi 2, komposisi 3",
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    NutriSenseTheme {
        HomeScreen(navController = navController, onScanFoodClick = {})
    }
}

