package com.example.dmitry.grades.domain.repositories.movie

import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import javax.inject.Inject

class MovieConfigRepository @Inject constructor(private val privateDataSource: PrivateDataSource) {

    var sortBy: String?
        get() = privateDataSource.sortBy
        set(value) {
            privateDataSource.sortBy = value
        }
}