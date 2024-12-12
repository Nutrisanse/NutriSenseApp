package com.example.nutrisense.data.resource.response.success

import com.google.gson.annotations.SerializedName

// Data class for the response payload
data class SexUpdateResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)