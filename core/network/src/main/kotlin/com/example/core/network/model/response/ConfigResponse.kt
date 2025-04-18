package com.example.core.network.model.response

import com.example.core.network.model.ImageConfigDTO
import com.google.gson.annotations.SerializedName

data class ConfigResponse(
    @SerializedName("images") val imageConfig: ImageConfigDTO,
)