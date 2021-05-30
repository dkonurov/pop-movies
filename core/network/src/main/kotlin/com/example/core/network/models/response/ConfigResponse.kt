package com.example.core.network.models.response

import com.example.core.network.models.ImageConfigDTO
import com.google.gson.annotations.SerializedName

data class ConfigResponse(@SerializedName("images") val imageConfig: ImageConfigDTO)