package com.example.dmitry.grades.domain.data.preferences

import com.example.dmitry.grades.domain.models.ImageConfig

interface PrivateDataSource {
    var baseUrlImg: String?

    var posterSizes: List<String>?

    var logoSizes: List<String>?

    fun saveImageConfig(imageConfig: ImageConfig?)
}