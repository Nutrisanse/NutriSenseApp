package com.example.nutrisense.ui.input.height

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.nutrisense.R
import com.example.nutrisense.ui.input.UserInputViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.items // Pastikan untuk mengimpor items

@Composable
fun HeightInputScreen(
    userInputViewModel: UserInputViewModel = viewModel(),
    navController: NavController
) {
    val userData by userInputViewModel.userData.collectAsState()
    val selectedHeight = userData.height ?: 163

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.height))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    val numberList = (100..250).toList()
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = numberList.indexOf(selectedHeight))

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

            Box(modifier = Modifier.size(200.dp)) {
                LottieAnimation(
                    composition = composition,
                    progress = progress
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .height(150.dp)
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(numberList) { height ->
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

            LaunchedEffect(listState.firstVisibleItemIndex, listState.layoutInfo.visibleItemsInfo) {
                val visibleItems = listState.layoutInfo.visibleItemsInfo
                if (visibleItems.isNotEmpty()) {
                    val middleIndex = visibleItems.size / 2
                    val centerItem = visibleItems[middleIndex]

                    // Periksa apakah index ada dalam daftar
                    if (centerItem.index in numberList.indices) {
                        val newHeight = numberList[centerItem.index]
                        userInputViewModel.updateHeight(newHeight, onSuccess = {
                            // Handle success, if any
                        }, onError = { error ->
                            Log.e("HeightInputScreen", "Error updating height: $error")
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "$selectedHeight cm",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Why we ask",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    userInputViewModel.updateHeight(selectedHeight, onSuccess = {

                    }, onError = { exception ->
                        Log.e("HeightInputScreen", "Error updating height: ${exception.message}")  // Akses message dari Exception
                    })
                    navController.navigate("age_input")
                },
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
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}
