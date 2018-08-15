package com.example.dmitry.grades.domain.data.preferences

import com.example.dmitry.grades.domain.models.ImageConfig

interface PrivateDataSource {
    var baseUrlImg: String?

    var posterSizes: List<String>?

    val posterSize: String?

    var logoSizes: List<String>?

    var sortBy: String?

    fun saveImageConfig(imageConfig: ImageConfig?)
}