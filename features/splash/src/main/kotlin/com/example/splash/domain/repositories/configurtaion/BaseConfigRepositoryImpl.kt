package com.example.splash.domain.repositories.configurtaion

import com.example.core.data.preferences.PrivateDataSource
import com.example.core.data.remote.HttpDataSource
import com.example.core.models.config.ImageConfig
import javax.inject.Inject

internal class BaseConfigRepositoryImpl @Inject constructor(
    private val httpDataSource: HttpDataSource,
    private val privateDataSource: PrivateDataSource
) : BaseConfigRepository {

    override suspend fun getConfiguration(): ImageConfig {
        val response = httpDataSource.getConfiguration()
        privateDataSource.saveImageConfig(response.imageConfig)
        return response.imageConfig!!
    }
}