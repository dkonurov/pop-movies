package com.example.dmitry.grades.domain.repositories

import com.example.dmitry.grades.domain.data.db.MovieDao
import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.models.ImageConfig
import com.example.dmitry.grades.domain.models.Movie
import com.example.dmitry.grades.domain.models.response.ConfigResponse
import com.example.dmitry.grades.domain.models.response.DiscoverResponse
import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import io.reactivex.Single
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

    @Mock
    private lateinit var movieDao: MovieDao

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getConfigurationEmptyTest() {
        // Arrange
        `when`(httpDataSource.getConfiguration()).thenReturn(Single.just(ConfigResponse(null)))

        // Act
        val testObserver = httpRepository.getConfiguration().test()

        // Asserts
        testObserver.assertError {
            it is NullPointerException
        }
    }


    @Test
    fun getConfigurationTest() {
        // Arrange
        val config = ImageConfig("test", listOf("1"), listOf("2"))
        `when`(httpDataSource.getConfiguration()).thenReturn(
                Single.just(ConfigResponse(config)))
        `when`(privateDataSource.baseUrlImg).thenReturn("test")

        // Act
        val testObserver = httpRepository.getConfiguration().test()

        // Asserts
        testObserver.assertValue(config)
        verify(httpDataSource).getConfiguration()
        verify(privateDataSource).saveImageConfig(config)
    }

    @Test
    fun getMoviesErrorTest() {
        // Arrange
        val error = Throwable()
        `when`(httpDataSource.getMovies()).thenReturn(Single.error(error))

        // Act
        val testObserver = httpRepository.getMovies().test()

        // Asserts
        testObserver.assertError(error)
    }

    @Test
    fun getMoviesTest() {
        // Arrange
        val list = prepareList()
        val response = DiscoverResponse(10, 10, 10, list)
        `when`(httpDataSource.getMovies()).thenReturn(Single.just(response))
        `when`(movieDao.getAll()).thenReturn(Single.just(list))

        // Act
        val testObserver = httpRepository.getMovies().test()

        // Asserts
        testObserver.assertValue(MovieListInfo(10, list))
        verify(movieDao).save(list)
    }

    private fun prepareList(): List<Movie> {
        val movie = Movie(1, 1, "test", false, "test",
                "test", listOf(1, 2, 3), "test", "test",
                "test", "test", 0.0, 1, false, 0.2)
        return listOf(movie)
    }

}