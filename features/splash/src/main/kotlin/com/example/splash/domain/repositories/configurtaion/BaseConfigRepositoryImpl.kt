package com.example.splash.domain.repositories.configurtaion

import com.example.core.data.preferences.PrivateDataSource
import com.example.core.network.models.ImageConfigDTO
import com.example.core.network.remote.HttpDataSource
import javax.inject.Inject

internal class BaseConfigRepositoryImpl @Inject constructor(
    private val httpDataSource: HttpDataSource,
    private val privateDataSource: PrivateDataSource
) : BaseConfigRepository {

    override suspend fun getConfiguration(): ImageConfigDTO {
        val response = httpDataSource.getConfiguration()
        privateDataSource.saveImageConfig(response.imageConfig)
        return response.imageConfig
    }
}