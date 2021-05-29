package com.example.core.data.remote

import com.example.core.models.DetailsMovie
import com.example.core.models.entity.Movie
import com.example.core.models.response.ConfigResponse
import com.example.core.models.response.DiscoverResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HttpDataSource {

    @GET("configuration")
    suspend fun getConfiguration(): ConfigResponse

    @GET("discover/movie")
    suspend fun getListMovies(
        @Query("page") page: Int? = null,
        @Query("sort_by") sortBy: String? = null
    ): DiscoverResponse

    @GET("movie/{id}")
    suspend fun getDetailsMovie(@Path("id") id: Long, @Query("language") language: String? = "en-US"): DetailsMovie

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Long, @Query("language") language: String? = "en-US"): Movie
}