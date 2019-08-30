package com.example.bottom.navigation.domain.config

import com.example.core.data.preferences.PrivateDataSource
import javax.inject.Inject

internal class SortConfigRepositoryImpl @Inject constructor(
    private val privateDataSource: PrivateDataSource
) : SortConfigRepository {

    override var sortBy: String?
        get() = privateDataSource.sortBy
        set(value) {
            privateDataSource.sortBy = value
        }
}