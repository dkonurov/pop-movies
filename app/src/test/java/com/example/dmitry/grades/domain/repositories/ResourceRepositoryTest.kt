package com.example.dmitry.grades.domain.repositories

import com.example.dmitry.grades.DebugAppDelegate
import com.example.dmitry.grades.R
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = DebugAppDelegate::class)
class ResourceRepositoryTest {

    private lateinit var resourceRepository: ResourceRepository

    @Before
    fun setup() {
        resourceRepository = ResourceRepository(DebugAppDelegate.context)
    }

    @Test
    fun testNetworkError() {
        // Arrange
        val expected = DebugAppDelegate.context.getString(R.string.network_error)

        // Act
        val error = resourceRepository.getNetworkError()

        // Asserts
        assertEquals(expected, error)
    }
}