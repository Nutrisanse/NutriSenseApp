package com.example.nutrisense.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(
    text: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface)
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(text = label, color = MaterialTheme.colorScheme.primary) }, // Label warna dinamis
        singleLine = true,
        textStyle = textStyle.copy(color = MaterialTheme.colorScheme.onSurface), // Teks warna dinamis
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary, // Border fokus
            unfocusedBorderColor = MaterialTheme.colorScheme.outline, // Border default
            cursorColor = MaterialTheme.colorScheme.primary // Warna kursor
        ),
        modifier = modifier.fillMaxWidth()
    )
}

