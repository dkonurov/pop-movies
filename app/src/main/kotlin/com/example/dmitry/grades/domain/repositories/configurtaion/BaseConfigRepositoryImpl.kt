package com.example.dmitry.grades.domain.repositories.configurtaion

import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.models.ImageConfig
import io.reactivex.Single
import javax.inject.Inject

class BaseConfigRepositoryImpl @Inject constructor(private val httpDataSource: HttpDataSource,
                                                   private val privateDataSource: PrivateDataSource) : BaseConfigRepository {

    override fun getConfiguration(): Single<ImageConfig> {
        return httpDataSource.getConfiguration()
                .map {
                    privateDataSource.saveImageConfig(it.imageConfig)
                    return@map it.imageConfig
                }
    }
}