package com.example.splash.domain.repositories.configurtaion

import com.example.core.network.models.response.ConfigResponse
import com.example.core.network.remote.HttpDataSource
import com.example.core.storage.db.entity.LocalImageConfig
import com.example.core.storage.preferences.PrivateDataSource
import javax.inject.Inject

internal class BaseConfigRepositoryImpl @Inject constructor(
    private val httpDataSource: HttpDataSource,
    private val privateDataSource: PrivateDataSource
) : BaseConfigRepository {

    override suspend fun getConfiguration(): Result<LocalImageConfig> = runCatching {
        val response = httpDataSource.getConfiguration()
        mapToLocal(response).also { privateDataSource.saveImageConfig(it) }
    }

    private fun mapToLocal(response: ConfigResponse) = LocalImageConfig(
        response.imageConfig.baseUrl,
        response.imageConfig.logosSize,
        response.imageConfig.postersSize
    )
}