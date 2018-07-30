package com.example.dmitry.grades.domain.data.remote

import com.example.dmitry.grades.domain.models.response.ConfigResponse
import io.reactivex.Flowable
import retrofit2.http.GET

interface HttpDataSource {

    @GET("configuration")
    fun getConfiguration(): Flowable<ConfigResponse>
}