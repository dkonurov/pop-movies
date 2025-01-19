package com.example.core.network.remote

import com.example.core.network.model.DetailsMovieDTO
import com.example.core.network.model.MovieDTO
import com.example.core.network.model.response.ConfigResponse
import com.example.core.network.model.response.DiscoverResponse
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
    suspend fun getDetailsMovie(@Path("id") id: Long, @Query("language") language: String? = "en-US"): DetailsMovieDTO

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Long, @Query("language") language: String? = "en-US"): MovieDTO
}