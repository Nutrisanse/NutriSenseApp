package com.example.nutrisense

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nutrisense.navigation.NutriSenseApp
import com.example.nutrisense.ui.theme.NutriSenseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriSenseTheme {
                NutriSenseApp(
                    onCameraLaunch = {
                        try {
                            val intent = Intent("android.media.action.IMAGE_CAPTURE")
                            startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(
                                this,
                                "Camera not supported on this device.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    showToast = { message ->
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}
