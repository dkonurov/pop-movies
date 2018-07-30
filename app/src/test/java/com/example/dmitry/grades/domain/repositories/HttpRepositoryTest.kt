package com.example.dmitry.grades.domain.repositories

import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.models.ImageConfig
import com.example.dmitry.grades.domain.models.response.ConfigResponse
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HttpRepositoryTest {

    @InjectMocks
    private lateinit var httpRepository: HttpRepository

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
        `when`(httpDataSource.getConfiguration()).thenReturn(Flowable.just(ConfigResponse(null)))

        // Act
        val testObserver = httpRepository.getConfiguration().test()

        // Asserts
        testObserver.assertValue(false)
    }


    @Test
    fun getConfigurationTest() {
        // Arrange
        val config = ImageConfig("test", listOf("1"), listOf("2"))
        `when`(httpDataSource.getConfiguration()).thenReturn(
                Flowable.just(ConfigResponse(config)))
        `when`(privateDataSource.baseUrlImg).thenReturn("test")

        // Act
        val testObserver = httpRepository.getConfiguration().test()

        // Asserts
        testObserver.assertValue(true)
        verify(httpDataSource).getConfiguration()
        verify(privateDataSource).saveImageConfig(config)
        verify(privateDataSource).baseUrlImg
    }

}