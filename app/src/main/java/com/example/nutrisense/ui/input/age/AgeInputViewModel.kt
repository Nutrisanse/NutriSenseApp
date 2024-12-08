package com.example.nutrisense.ui.input.age

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AgeInputViewModel : ViewModel() {

    // State untuk usia yang dipilih
    private val _selectedAge = MutableStateFlow(25) // Default age
    val selectedAge: StateFlow<Int> = _selectedAge

    // Fungsi untuk memperbarui usia yang dipilih
    fun updateSelectedAge(age: Int) {
        viewModelScope.launch {
            _selectedAge.emit(age)
        }
    }
}
