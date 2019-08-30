package com.example.core.data.remote

import com.example.core.models.DetailsMovie
import com.example.core.models.entity.Movie
import com.example.core.models.response.ConfigResponse
import com.example.core.models.response.DiscoverResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HttpDataSource {

    @GET("configuration")
    fun getConfiguration(): Call<ConfigResponse>

    @GET("discover/movie")
    fun getListMovies(
        @Query("page") page: Int? = null,
        @Query("sort_by") sortBy: String? = null
    ): Call<DiscoverResponse>

    @GET("movie/{id}")
    fun getDetailsMovie(@Path("id") id: Long, @Query("language") language: String? = "en-US"): Call<DetailsMovie>

    @GET("movie/{id}")
    fun getMovie(@Path("id") id: Long, @Query("language") language: String? = "en-US"): Call<Movie>
}