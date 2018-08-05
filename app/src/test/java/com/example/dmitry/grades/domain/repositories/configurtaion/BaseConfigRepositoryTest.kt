package com.example.dmitry.grades.domain.repositories.configurtaion

import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.models.ImageConfig
import com.example.dmitry.grades.domain.models.response.ConfigResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class BaseConfigRepositoryTest {

    @InjectMocks
    private lateinit var baseConfigRepository: BaseConfigRepository

    @Mock
    private lateinit var privateDataSource: PrivateDataSource

    @Mock
    private lateinit var httpDataSource: HttpDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getConfigurationEmptyTest() {
        // Arrange
        Mockito.`when`(httpDataSource.getConfiguration()).thenReturn(Single.just(ConfigResponse(null)))

        // Act
        val testObserver = baseConfigRepository.getConfiguration().test()

        // Asserts
        testObserver.assertError {
            it is NullPointerException
        }
    }


    @Test
    fun getConfigurationTest() {
        // Arrange
        val config = ImageConfig("test", listOf("1"), listOf("2"))
        Mockito.`when`(httpDataSource.getConfiguration()).thenReturn(
                Single.just(ConfigResponse(config)))
        Mockito.`when`(privateDataSource.baseUrlImg).thenReturn("test")

        // Act
        val testObserver = baseConfigRepository.getConfiguration().test()

        // Asserts
        testObserver.assertValue(config)
        Mockito.verify(httpDataSource).getConfiguration()
        Mockito.verify(privateDataSource).saveImageConfig(config)
    }
}