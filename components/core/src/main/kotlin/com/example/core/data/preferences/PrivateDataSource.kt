package com.example.core.data.preferences

import com.example.core.models.config.ImageConfig

interface PrivateDataSource {
    var baseUrlImg: String?

    var posterSizes: List<String>?

    val posterSize: String?

    var logoSizes: List<String>?

    var sortBy: String?

    fun saveImageConfig(imageConfig: ImageConfig?)
}