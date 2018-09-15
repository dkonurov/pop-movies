package com.example.dmitry.grades.domain.repositories.configurtaion

import com.example.dmitry.grades.domain.models.ImageConfig
import io.reactivex.Single

interface BaseConfigRepository {

    suspend fun getConfiguration(): ImageConfig
}