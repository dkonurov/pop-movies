package com.example.dmitry.grades.domain.models.response

import com.example.dmitry.grades.domain.models.ImageConfig
import com.google.gson.annotations.SerializedName

data class ConfigResponse(@SerializedName("images") val imageConfig: ImageConfig?)