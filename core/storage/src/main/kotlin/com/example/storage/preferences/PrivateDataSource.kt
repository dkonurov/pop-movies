package com.example.storage.preferences

import com.example.storage.db.entity.LocalImageConfig

interface PrivateDataSource {
    var baseUrlImg: String?

    var posterSizes: List<String>?

    val posterSize: String?

    var logoSizes: List<String>?

    var sortBy: String?

    fun saveImageConfig(config: LocalImageConfig)
}