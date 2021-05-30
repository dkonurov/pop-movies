package com.example.core.data.preferences

import com.example.core.network.models.ImageConfigDTO

interface PrivateDataSource {
    var baseUrlImg: String?

    var posterSizes: List<String>?

    val posterSize: String?

    var logoSizes: List<String>?

    var sortBy: String?

    fun saveImageConfig(config: ImageConfigDTO)
}