package com.example.dmitry.grades.domain.repositories

import android.text.TextUtils
import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import io.reactivex.Flowable
import javax.inject.Inject

class HttpRepository @Inject constructor(private val httpDataSource: HttpDataSource,
                                         private val privateDataSource: PrivateDataSource) {

    fun getConfiguration(): Flowable<Boolean> {
        return httpDataSource.getConfiguration()
                .map {
                    privateDataSource.saveImageConfig(it.imageConfig)
                    return@map !TextUtils.isEmpty(privateDataSource.baseUrlImg)
                }
    }
}