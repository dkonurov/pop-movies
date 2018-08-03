package com.example.dmitry.grades.domain.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.dmitry.grades.domain.models.ImageConfig
import javax.inject.Inject

class PrivateDataSourceImpl @Inject constructor(context: Context) : PrivateDataSource {

    override fun saveImageConfig(imageConfig: ImageConfig?) {
        imageConfig?.let {
            baseUrlImg = imageConfig.baseUrl
            logoSizes = imageConfig.logosSize
            posterSizes = imageConfig.postersSize
        }
    }

    companion object {
        private const val BASE_URL = "com.example.dmitry.grades.domain.data.preferences.base_url"
        private const val LOGO_SIZES = "com.example.dmitry.grades.domain.data.preferences.logo_sizes"
        private const val POSTERS_SIZES = "com.example.dmitry.grades.domain.data.preferences.posters_sizes"
        private const val LAST_PAGE_MOVIE = "com.example.dmitry.grades.domain.data.preferences.last_page_movie"
    }

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

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
}