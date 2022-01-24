package com.example.core.storage.preferences

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import com.example.core.storage.db.entity.LocalImageConfig
import javax.inject.Inject

internal class PrivateDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PrivateDataSource {

    companion object {
        private const val BASE_URL = "com.example.dmitry.grades.domain.data.preferences.base_url"
        private const val LOGO_SIZES = "com.example.dmitry.grades.domain.data.preferences.logo_sizes"
        private const val POSTERS_SIZES = "com.example.dmitry.grades.domain.data.preferences.posters_sizes"
        private const val SORT_BY = "com.example.dmitry.grades.domain.data.preferences.sort_by"

        @VisibleForTesting
        const val SORT_BY_DEFAULT = "popularity.desc"
    }

    override fun saveImageConfig(config: LocalImageConfig) {
        baseUrlImg = config.baseUrl
        logoSizes = config.logosSize
        posterSizes = config.postersSize
    }

    override val posterSize: String?
        get() {
            val size = posterSizes
            return if (size == null || size.isEmpty()) {
                null
            } else {
                size[0]
            }
        }

    override var baseUrlImg: String?
        get() = sharedPreferences.getString(BASE_URL, null)
        set(value) {
            sharedPreferences.edit().putString(BASE_URL, value).apply()
        }

    override var logoSizes: List<String>?
        get() = sharedPreferences.getStringSet(LOGO_SIZES, null)?.toList()
        set(value) {
            sharedPreferences.edit().putStringSet(LOGO_SIZES, value?.toSet()).apply()
        }

    override var posterSizes: List<String>?
        get() = sharedPreferences.getStringSet(POSTERS_SIZES, null)?.toList()
        set(value) {
            sharedPreferences.edit().putStringSet(POSTERS_SIZES, value?.toSet()).apply()
        }

    override var sortBy: String?
        get() = sharedPreferences.getString(SORT_BY, SORT_BY_DEFAULT)
        set(value) {
            sharedPreferences.edit().putString(SORT_BY, value).apply()
        }
}