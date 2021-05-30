package com.example.splash.domain.repositories.configurtaion

import com.example.core.network.models.ImageConfigDTO

internal interface BaseConfigRepository {

    suspend fun getConfiguration(): ImageConfigDTO
}