package com.example.dmitry.grades.domain.repositories.configurtaion

import com.example.core.models.config.ImageConfig

interface BaseConfigRepository {

    suspend fun getConfiguration(): ImageConfig
}