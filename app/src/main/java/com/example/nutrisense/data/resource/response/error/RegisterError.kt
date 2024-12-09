package com.example.nutrisense.data.resource.response.error

import com.google.gson.annotations.SerializedName

data class RegisterError(
    @field:SerializedName("detail")
    val message: String

)