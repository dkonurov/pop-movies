package com.example.splash.domain.repositories.configurtaion

import com.example.core.models.config.ImageConfig

internal interface BaseConfigRepository {

    suspend fun getConfiguration(): ImageConfig
}