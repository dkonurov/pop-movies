package com.example.dmitry.grades.domain.data.preferences

import com.example.dmitry.grades.DebugAppDelegate
import com.example.dmitry.grades.domain.models.ImageConfig
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = DebugAppDelegate::class)
class PrivateDataSourceImplTest {

    val privateDataSourceImpl = PrivateDataSourceImpl(DebugAppDelegate.context)

    @Test
    fun saveBaseUrlNull() {
        // Act
        privateDataSourceImpl.baseUrlImg = null

        // Assert
        assertNull(privateDataSourceImpl.baseUrlImg)
    }

    @Test
    fun saveBaseUrlEmpty() {
        // Act
        privateDataSourceImpl.baseUrlImg = ""

        // Assert
        assertEquals(privateDataSourceImpl.baseUrlImg, "")
    }

    @Test
    fun saveBaseUrl() {
        // Act
        privateDataSourceImpl.baseUrlImg = "test"

        // Assert
        assertEquals(privateDataSourceImpl.baseUrlImg, "test")
    }


    @Test
    fun savePosterSizesNull() {
        // Act
        privateDataSourceImpl.posterSizes = null

        // Assert
        assertNull(privateDataSourceImpl.posterSizes)
    }

    @Test
    fun savePosterSizesEmpty() {
        // Act
        privateDataSourceImpl.posterSizes = emptyList()

        // Assert
        assertEquals(privateDataSourceImpl.posterSizes, emptyList<String>())
    }

    @Test
    fun savePosterSizes() {
        // Arrange
        val list = listOf("1", "2", "3")

        // Act
        privateDataSourceImpl.posterSizes = list

        // Assert
        assertEquals(privateDataSourceImpl.posterSizes, list)
    }

    @Test
    fun saveLogSizeNull() {
        // Act
        privateDataSourceImpl.logoSizes = null

        // Assert
        assertNull(privateDataSourceImpl.logoSizes)
    }

    @Test
    fun saveLogoSizesEmpty() {
        // Act
        privateDataSourceImpl.logoSizes = emptyList()

        // Assert
        assertEquals(privateDataSourceImpl.logoSizes, emptyList<String>())
    }

    @Test
    fun saveLogoSizes() {
        // Arrange
        val list = listOf("1", "2", "3")

        // Act
        privateDataSourceImpl.logoSizes = list

        // Assert
        assertEquals(privateDataSourceImpl.logoSizes, list)
    }

    @Test
    fun saveImageConfNull() {
        // Act
        privateDataSourceImpl.saveImageConfig(null)

        // Asserts
        assertNull(privateDataSourceImpl.logoSizes)
        assertNull(privateDataSourceImpl.posterSizes)
        assertNull(privateDataSourceImpl.baseUrlImg)
    }

    @Test
    fun saveImageConfUrlNull() {
        // Arrange
        val posters = listOf("1", "2", "3")
        val logos = listOf("4", "5", "6")
        val config = ImageConfig(null, logos, posters)

        // Act
        privateDataSourceImpl.saveImageConfig(config)

        // Asserts
        assertNull(privateDataSourceImpl.baseUrlImg)
    }

    @Test
    fun saveImageConfPostersNull() {
        // Arrange
        val logos = listOf("4", "5", "6")
        val config = ImageConfig("test", logos, null)

        // Act
        privateDataSourceImpl.saveImageConfig(config)

        // Asserts
        assertNull(privateDataSourceImpl.posterSizes)
    }

    @Test
    fun saveImageConfLogosNull() {
        // Arrange
        val posters = listOf("4", "5", "6")
        val config = ImageConfig("test", null, posters)

        // Act
        privateDataSourceImpl.saveImageConfig(config)

        // Asserts
        assertNull(privateDataSourceImpl.logoSizes)
    }

    @Test
    fun saveImageConf() {
        // Arrange
        val posters = listOf("1", "2", "3")
        val logos = listOf("4", "5", "6")
        val config = ImageConfig("test", logos, posters)

        // Act
        privateDataSourceImpl.saveImageConfig(config)

        // Asserts
        assertEquals(privateDataSourceImpl.logoSizes, logos)
        assertEquals(privateDataSourceImpl.posterSizes, posters)
        assertEquals(privateDataSourceImpl.baseUrlImg, "test")
    }

}