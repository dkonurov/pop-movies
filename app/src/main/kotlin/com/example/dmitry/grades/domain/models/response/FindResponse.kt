package com.example.dmitry.grades.domain.models.response

import android.graphics.Movie
import com.google.gson.annotations.SerializedName

data class FindResponse(@SerializedName("movie_results") val movies: List<Movie>) {
}