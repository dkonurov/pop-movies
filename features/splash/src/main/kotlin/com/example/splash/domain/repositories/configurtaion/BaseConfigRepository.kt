package com.example.splash.domain.repositories.configurtaion

import com.example.core.storage.db.entity.LocalImageConfig

internal interface BaseConfigRepository {

    suspend fun getConfiguration(): Result<LocalImageConfig>
}