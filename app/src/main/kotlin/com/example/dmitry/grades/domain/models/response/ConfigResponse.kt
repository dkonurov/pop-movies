package com.example.dmitry.grades.domain.models.response

import com.example.core.models.config.ImageConfig
import com.google.gson.annotations.SerializedName

data class ConfigResponse(@SerializedName("images") val imageConfig: ImageConfig?)