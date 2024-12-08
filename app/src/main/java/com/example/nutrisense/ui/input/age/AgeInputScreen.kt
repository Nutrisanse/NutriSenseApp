package com.example.nutrisense.ui.input.age

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
fun AgeInputScreen(
    viewModel: AgeInputViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNextClicked: (Int) -> Unit
) {
    val selectedAge by viewModel.selectedAge.collectAsState() // Observe age state

    // Load Lottie animation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.calendar))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    val numberList = (10..100).toList()
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "How old are you?",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.5.sp
                ),
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Animation
            Box(
                modifier = Modifier.size(180.dp)
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = progress
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Rolling Picker
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .padding(vertical = 8.dp)
            ) {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(numberList.size) { index ->
                        val age = numberList[index]
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "$age",
                                style = TextStyle(
                                    fontSize = if (age == selectedAge) 32.sp else 20.sp,
                                    fontWeight = if (age == selectedAge) FontWeight.Bold else FontWeight.Normal,
                                    color = if (age == selectedAge) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                                ),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }

            // Update selected age when scrolling
            LaunchedEffect(listState.firstVisibleItemIndex, listState.layoutInfo.visibleItemsInfo) {
                val visibleItems = listState.layoutInfo.visibleItemsInfo
                if (visibleItems.size >= 3) {
                    val centerItem = visibleItems[1]
                    viewModel.updateSelectedAge(numberList[centerItem.index])
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Display selected age
            Text(
                text = "$selectedAge years",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // "Why we ask" text
            Text(
                text = "Why we ask",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

        // Bottom Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { onNextClicked(selectedAge) },
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

            Text(
                text = "By continuing, you agree to NutriSense\nPrivacy Policy and Terms of Use",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}


@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AgeInputScreenPreview() {
    NutriSenseTheme {
        AgeInputScreen(onNextClicked = {})
    }
}
