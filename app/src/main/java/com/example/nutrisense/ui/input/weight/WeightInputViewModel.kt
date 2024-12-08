package com.example.nutrisense.ui.input.weight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeightInputViewModel : ViewModel() {

    // StateFlow untuk menyimpan berat badan
    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> = _weight

    // Fungsi untuk memperbarui berat badan
    fun updateWeight(newWeight: String) {
        viewModelScope.launch {
            // Hanya memperbarui jika input valid (angka)
            if (newWeight.all { it.isDigit() }) {
                _weight.value = newWeight
            }
        }
    }
}
