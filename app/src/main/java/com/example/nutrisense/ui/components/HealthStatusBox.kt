package com.example.nutrisense.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
            .shadow(elevation = 4.dp), // Tambahkan bayangan
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
