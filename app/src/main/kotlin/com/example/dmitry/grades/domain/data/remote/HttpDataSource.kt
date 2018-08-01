package com.example.dmitry.grades.domain.data.remote

import com.example.dmitry.grades.domain.models.response.ConfigResponse
import com.example.dmitry.grades.domain.models.response.DiscoverResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface HttpDataSource {

    @GET("configuration")
    fun getConfiguration(): Single<ConfigResponse>

    @GET("discover")
    fun getMovies(@Query("page") page: Int? = null,
                  @Query("sort_by") sortBy: String? = null,
                  @Query("year") year: Int? = null): Single<DiscoverResponse>
}