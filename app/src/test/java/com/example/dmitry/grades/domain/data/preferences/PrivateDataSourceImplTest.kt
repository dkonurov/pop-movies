package com.example.dmitry.grades.domain.data.preferences

import com.example.dmitry.grades.DebugAppDelegate
import com.example.dmitry.grades.domain.models.ImageConfig
import junit.framework.Assert
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
        // Arrange
        privateDataSourceImpl.baseUrlImg = "test"

        // Act
        privateDataSourceImpl.baseUrlImg = null

        // Assert
        Assert.assertNull(privateDataSourceImpl.baseUrlImg)
    }

    @Test
    fun saveBaseUrlEmpty() {
        // Arrange
        privateDataSourceImpl.baseUrlImg = "test"

        // Act
        privateDataSourceImpl.baseUrlImg = ""

        // Assert
        Assert.assertEquals(privateDataSourceImpl.baseUrlImg, "")
    }

    @Test
    fun saveBaseUrl() {
        // Arrange
        privateDataSourceImpl.baseUrlImg = "test1"

        // Act
        privateDataSourceImpl.baseUrlImg = "test"

        // Assert
        Assert.assertEquals(privateDataSourceImpl.baseUrlImg, "test")
    }


    @Test
    fun savePosterSizesNull() {
        // Arrange
        privateDataSourceImpl.posterSizes = listOf("1", "2")

        // Act
        privateDataSourceImpl.posterSizes = null

        // Assert
        Assert.assertNull(privateDataSourceImpl.posterSizes)
    }

    @Test
    fun savePosterSizesEmpty() {
        // Arrange
        privateDataSourceImpl.posterSizes = listOf("1", "2")

        // Act
        privateDataSourceImpl.posterSizes = emptyList()

        // Assert
        Assert.assertEquals(privateDataSourceImpl.posterSizes, emptyList<String>())
    }

    @Test
    fun savePosterSizes() {
        // Arrange
        privateDataSourceImpl.posterSizes = listOf("4", "5")
        val list = listOf("1", "2", "3")

        // Act
        privateDataSourceImpl.posterSizes = list

        // Assert
        Assert.assertEquals(privateDataSourceImpl.posterSizes, list)
    }

    @Test
    fun saveLogoSizeNull() {
        // Arrange
        privateDataSourceImpl.logoSizes = listOf("1", "2")

        // Act
        privateDataSourceImpl.logoSizes = null

        // Assert
        Assert.assertNull(privateDataSourceImpl.logoSizes)
    }

    @Test
    fun saveLogoSizesEmpty() {
        // Arrange
        privateDataSourceImpl.logoSizes = listOf("1", "2")

        // Act
        privateDataSourceImpl.logoSizes = emptyList()

        // Assert
        Assert.assertEquals(privateDataSourceImpl.logoSizes, emptyList<String>())
    }

    @Test
    fun saveLogoSizes() {
        // Arrange
        privateDataSourceImpl.logoSizes = listOf("4", "5")
        val list = listOf("1", "2", "3")

        // Act
        privateDataSourceImpl.logoSizes = list

        // Assert
        Assert.assertEquals(privateDataSourceImpl.logoSizes, list)
    }

    @Test
    fun saveImageConfNull() {
        // Arrange
        privateDataSourceImpl.saveImageConfig(ImageConfig("test", listOf("1", "2"), listOf("3", "4")))

        // Act
        privateDataSourceImpl.saveImageConfig(null)

        // Asserts
        Assert.assertNull(privateDataSourceImpl.logoSizes)
        Assert.assertNull(privateDataSourceImpl.posterSizes)
        Assert.assertNull(privateDataSourceImpl.baseUrlImg)
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
        Assert.assertNull(privateDataSourceImpl.baseUrlImg)
    }

    @Test
    fun saveImageConfPostersNull() {
        // Arrange
        val logos = listOf("4", "5", "6")
        val config = ImageConfig("test", logos, null)

        // Act
        privateDataSourceImpl.saveImageConfig(config)

        // Asserts
        Assert.assertNull(privateDataSourceImpl.posterSizes)
    }

    @Test
    fun saveImageConfLogosNull() {
        // Arrange
        val posters = listOf("4", "5", "6")
        val config = ImageConfig("test", null, posters)

        // Act
        privateDataSourceImpl.saveImageConfig(config)

        // Asserts
        Assert.assertNull(privateDataSourceImpl.logoSizes)
    }

    @Test
    fun saveImageConf() {
        // Arrange
        privateDataSourceImpl.saveImageConfig(ImageConfig("test", listOf("1", "2"), listOf("3", "4")))
        val posters = listOf("1", "2", "3")
        val logos = listOf("4", "5", "6")
        val config = ImageConfig("test", logos, posters)

        // Act
        privateDataSourceImpl.saveImageConfig(config)

        // Asserts
        Assert.assertEquals(privateDataSourceImpl.logoSizes, logos)
        Assert.assertEquals(privateDataSourceImpl.posterSizes, posters)
        Assert.assertEquals(privateDataSourceImpl.baseUrlImg, "test")
    }

    @Test
    fun sortBySaveNullTest() {
        // Arrange
        privateDataSourceImpl.sortBy = "test"

        // Act
        privateDataSourceImpl.sortBy = null

        // Asserts
        Assert.assertEquals(privateDataSourceImpl.sortBy, PrivateDataSourceImpl.SORT_BY_DEFAULT)
    }

    @Test
    fun sortBySaveEmptyTest() {
        // Arrange
        privateDataSourceImpl.sortBy = "test"

        // Act
        privateDataSourceImpl.sortBy = ""

        // Asserts
        Assert.assertEquals(privateDataSourceImpl.sortBy, "")
    }

    @Test
    fun sortBySaveTest() {
        // Arrange
        privateDataSourceImpl.sortBy = "test"

        // Act
        privateDataSourceImpl.sortBy = "test2"

        // Asserts
        Assert.assertEquals(privateDataSourceImpl.sortBy, "test2")
    }

}