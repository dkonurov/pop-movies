package com.example.dmitry.grades.di.modules

import android.content.Context
import android.os.Build
import com.example.dmitry.grades.BuildConfig
import com.example.dmitry.grades.di.BaseUrl
import com.example.dmitry.grades.di.providers.PrivateDataSourceProvider
import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.models.config.AppInfo
import com.example.dmitry.grades.domain.repositories.AppInfoRepository
import toothpick.config.Module

class AppModule(context: Context) : Module() {

    init {
        bind(Context::class.java).toInstance(context)
        bind(String::class.java).withName(BaseUrl::class.java).toInstance(BuildConfig.PROD_URL)
        bind(AppInfo::class.java).toInstance(AppInfo(BuildConfig.VERSION_CODE, BuildConfig.DEBUG, Build.VERSION.SDK_INT))
        bind(AppInfoRepository::class.java)
        bind(PrivateDataSource::class.java).toProvider(PrivateDataSourceProvider::class.java).providesSingletonInScope()
    }
}