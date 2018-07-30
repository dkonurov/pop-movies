package com.example.dmitry.grades.domain.repositories

import com.example.dmitry.grades.domain.models.config.AppInfo
import javax.inject.Inject

class AppInfoRepository @Inject constructor(appInfo: AppInfo) {

    val versionCode: Int = appInfo.versionCode

    val isDebug: Boolean = appInfo.isDebug

    val versionSdk: Int = appInfo.versionSdk
}