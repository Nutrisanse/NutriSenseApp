package com.example.nutrisense.ui.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HeightInputViewModel : ViewModel() {

    // Backing property for selected height
    private val _selectedHeight = MutableStateFlow(163) // Default height is 163 cm
    val selectedHeight: StateFlow<Int> = _selectedHeight

    // Function to update the selected height
    fun updateHeight(height: Int) {
        viewModelScope.launch {
            _selectedHeight.emit(height)
        }
    }
}

