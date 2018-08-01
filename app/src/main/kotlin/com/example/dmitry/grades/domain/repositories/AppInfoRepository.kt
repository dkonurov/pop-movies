package com.example.dmitry.grades.domain.repositories

import com.example.dmitry.grades.domain.models.config.AppInfo
import javax.inject.Inject

open class AppInfoRepository @Inject constructor(appInfo: AppInfo) {

    open val versionCode: Int = appInfo.versionCode

    open val isDebug: Boolean = appInfo.isDebug

    open val versionSdk: Int = appInfo.versionSdk
}