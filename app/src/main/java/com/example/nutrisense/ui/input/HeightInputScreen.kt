package com.example.nutrisense.ui.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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

@Composable
fun HeightInputScreen(onHeightSubmitted: (String) -> Unit) {
    // State for selected height, initialized to 163 cm
    var selectedHeight by remember { mutableStateOf(163) }

    // Load Lottie animation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.height)) // Use height.json file
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever // Loop indefinitely
    )

    val numberList = (100..250).toList()
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = numberList.indexOf(163))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Gunakan warna latar dari tema
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        // Top Section (Title, Animation, Picker, and "Why we ask")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "How tall are you?",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

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

            // Rolling Picker
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .height(150.dp) // Enough to show 3 items
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(numberList.size) { index ->
                    val height = numberList[index]
                    Text(
                        text = "$height",
                        style = TextStyle(
                            fontSize = if (height == selectedHeight) 30.sp else 20.sp,
                            fontWeight = if (height == selectedHeight) FontWeight.Bold else FontWeight.Normal,
                            color = if (height == selectedHeight)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Update selected height based on scrolling
            LaunchedEffect(listState.firstVisibleItemIndex, listState.layoutInfo.visibleItemsInfo) {
                val visibleItems = listState.layoutInfo.visibleItemsInfo
                if (visibleItems.isNotEmpty()) {
                    // Select the middle visible item
                    val middleIndex = visibleItems.size / 2
                    val centerItem = visibleItems[middleIndex]
                    selectedHeight = numberList[centerItem.index]
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Display selected height
            Text(
                text = "$selectedHeight cm",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // "Why we ask" text
            Text(
                text = "Why we ask",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

        // Bottom Section (Button and Footer)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Next Button
            Button(
                onClick = { onHeightSubmitted("$selectedHeight") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Next",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer Text
            Text(
                text = "By continuing, you agree to NutriSense\nPrivacy Policy and Terms of Use",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeightInputScreenPreview() {
    NutriSenseTheme {
        HeightInputScreen(onHeightSubmitted = { /* Handle height submission */ })
    }
}
