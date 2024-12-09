package com.example.nutrisense

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nutrisense.navigation.NutriSenseApp
import com.example.nutrisense.ui.theme.NutriSenseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriSenseTheme {
                NutriSenseApp(
                    onCameraLaunch = {
                        launchCamera()
                    },
                    showToast = { message ->
                        showToast(message)
                    }
                )
            }
        }
    }

    private fun launchCamera() {
        try {
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                showToast("Camera app not found.")
            }
        } catch (e: ActivityNotFoundException) {
            showToast("Camera not supported on this device.")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
