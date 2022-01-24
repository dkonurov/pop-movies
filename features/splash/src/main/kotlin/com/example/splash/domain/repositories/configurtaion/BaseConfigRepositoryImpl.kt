package com.example.splash.domain.repositories.configurtaion

import com.example.core.network.models.ImageConfigDTO
import com.example.core.network.models.response.ConfigResponse
import com.example.core.network.remote.HttpDataSource
import com.example.core.storage.db.entity.LocalImageConfig
import com.example.core.storage.preferences.PrivateDataSource
import javax.inject.Inject

internal class BaseConfigRepositoryImpl @Inject constructor(
    private val httpDataSource: HttpDataSource,
    private val privateDataSource: PrivateDataSource
) : BaseConfigRepository {

    override suspend fun getConfiguration(): ImageConfigDTO {
        val response = httpDataSource.getConfiguration()
        val config = mapToLocal(response)
        privateDataSource.saveImageConfig(config)
        return response.imageConfig
    }

    private fun mapToLocal(response: ConfigResponse) = LocalImageConfig(
        response.imageConfig.baseUrl,
        response.imageConfig.logosSize,
        response.imageConfig.postersSize
    )
}