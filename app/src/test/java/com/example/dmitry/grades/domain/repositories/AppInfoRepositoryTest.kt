package com.example.dmitry.grades.domain.repositories

import com.example.dmitry.grades.domain.models.config.AppInfo
import org.junit.Assert.assertEquals
import org.junit.Test

class AppInfoRepositoryTest {

    private val appInfoRepository = AppInfoRepository(AppInfo(10, false, 15))

    @Test
    fun appInfoTest() {
        // Asserts
        assertEquals(appInfoRepository.isDebug, false)
        assertEquals(appInfoRepository.versionCode, 10)
        assertEquals(appInfoRepository.versionSdk, 15)
    }
}