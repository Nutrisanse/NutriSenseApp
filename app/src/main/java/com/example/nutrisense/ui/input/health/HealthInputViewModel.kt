package com.example.nutrisense.ui.input

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HealthInputViewModel : ViewModel() {

    // StateFlow to hold input values
    private val _bloodPressure = MutableStateFlow("")
    val bloodPressure: StateFlow<String> = _bloodPressure

    private val _cholesterol = MutableStateFlow("")
    val cholesterol: StateFlow<String> = _cholesterol

    private val _sugarLevel = MutableStateFlow("")
    val sugarLevel: StateFlow<String> = _sugarLevel

    private val _allergies = MutableStateFlow("")
    val allergies: StateFlow<String> = _allergies

    // Update functions for each field
    fun updateBloodPressure(value: String) {
        _bloodPressure.value = value
    }

    fun updateCholesterol(value: String) {
        _cholesterol.value = value
    }

    fun updateSugarLevel(value: String) {
        _sugarLevel.value = value
    }

    fun updateAllergies(value: String) {
        _allergies.value = value
    }

    // Collect health data into a single string
    fun getHealthData(): String {
        return "Blood Pressure: ${_bloodPressure.value}, " +
                "Cholesterol: ${_cholesterol.value}, " +
                "Sugar Level: ${_sugarLevel.value}, " +
                "Allergies: ${_allergies.value}"
    }
}
