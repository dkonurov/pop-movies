package com.example.core.models.config

import com.google.gson.annotations.SerializedName

data class ImageConfig(
    @SerializedName("base_url") val baseUrl: String?,
    @SerializedName("logo_sizes") val logosSize: List<String>?,
    @SerializedName("poster_sizes") val postersSize: List<String>?
)