package com.example.dmitry.grades.domain.repositories.configurtaion

import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.models.ImageConfig
import com.example.dmitry.grades.ui.base.extensions.await
import javax.inject.Inject

class BaseConfigRepositoryImpl @Inject constructor(private val httpDataSource: HttpDataSource,
                                                   private val privateDataSource: PrivateDataSource) : BaseConfigRepository {

    override suspend fun getConfiguration(): ImageConfig {
        val response = httpDataSource.getConfiguration().await()
        privateDataSource.saveImageConfig(response.imageConfig)
        return response.imageConfig!!
    }
}