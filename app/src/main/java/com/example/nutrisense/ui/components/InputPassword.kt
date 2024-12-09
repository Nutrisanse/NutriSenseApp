package com.example.nutrisense.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputPassword(
    passwordVisible: Boolean,
    value: String,
    togglePasswordVisibility: () -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Password", color = MaterialTheme.colorScheme.primary) }, // Label dinamis
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = togglePasswordVisibility) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary // Ikon warna dinamis
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary, // Border fokus
            unfocusedBorderColor = MaterialTheme.colorScheme.outline, // Border default
            cursorColor = MaterialTheme.colorScheme.primary // Warna kursor
        ),
        modifier = modifier.fillMaxWidth()
    )
}


