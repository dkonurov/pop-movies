package com.example.core.network.models

import com.google.gson.annotations.SerializedName

data class ImageConfigDTO(
    @SerializedName("base_url") val baseUrl: String?,
    @SerializedName("logo_sizes") val logosSize: List<String>?,
    @SerializedName("poster_sizes") val postersSize: List<String>?
)