package com.example.nutrisense.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutrisense.ui.theme.AppTheme

@Composable
fun AppButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    type: AppButtonType = AppButtonType.Filled,
    buttonColor: Color = MaterialTheme.colorScheme.primary, // Default warna tombol
    textColor: Color = MaterialTheme.colorScheme.onPrimary, // Default warna teks
    onClick: () -> Unit
) {
    when (type) {
        AppButtonType.Filled -> Button(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = textColor,
                disabledContainerColor = buttonColor.copy(alpha = 0.5f),
                disabledContentColor = textColor.copy(alpha = 0.5f)
            ),
            shape = shape
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                color = textColor
            )
        }

        AppButtonType.Outlined -> OutlinedButton(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            shape = shape,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = buttonColor,
                disabledContentColor = buttonColor.copy(alpha = 0.5f)
            )
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                color = textColor
            )
        }

        AppButtonType.Text -> {
            TextButton(
                onClick = onClick,
                modifier = modifier.fillMaxWidth(),
                enabled = enabled,
                shape = shape
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleSmall,
                    color = textColor
                )
            }
        }
    }
}


@Composable
private fun ButtonContent(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onPrimary), // Teks dinamis
        modifier = Modifier.padding(8.dp)
    )
}

@Preview
@Composable
fun PreviewOutlinedButton() {
    AppTheme {
        AppButton(text = "Outlined", type = AppButtonType.Outlined) {
        }
    }
}

@Preview
@Composable
fun PreviewTextButton() {
    AppTheme {
        AppButton(text = "Text Button", type = AppButtonType.Text) {
        }
    }
}

@Preview(name = "Primary Button")
@Composable
fun PreviewAppButton() {
    AppTheme {
        AppButton(text = "Mantap") {}
    }
}

@Preview(name = "Disabled Button")
@Composable
fun PreviewDisabledAppButton() {
    AppTheme {
        AppButton(text = "Text", enabled = false) {}
    }
}

enum class AppButtonType {
    Filled, Outlined, Text
}