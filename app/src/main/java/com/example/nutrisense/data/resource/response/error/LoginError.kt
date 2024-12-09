package com.example.nutrisense.data.resource.response.error

import com.google.gson.annotations.SerializedName

data class LoginError(

    @field:SerializedName("detail")
    val detail: List<DetailItem?>? = null
)

data class DetailItem(

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("loc")
    val loc: List<String?>? = null,

    @field:SerializedName("input")
    val input: Any? = null,

    @field:SerializedName("type")
    val type: String? = null
)