package com.example.dmitry.grades.domain.repositories.movie

import com.example.dmitry.grades.domain.data.db.MovieDao
import com.example.dmitry.grades.domain.data.preferences.PrivateDataSource
import com.example.dmitry.grades.domain.data.remote.HttpDataSource
import com.example.dmitry.grades.domain.mappers.MovieMapper
import com.example.dmitry.grades.domain.models.DetailsMovie
import com.example.dmitry.grades.domain.models.entity.Movie
import com.example.dmitry.grades.domain.models.response.DiscoverResponse
import com.example.dmitry.grades.domain.models.ui.MovieListInfo
import com.example.dmitry.grades.domain.models.ui.ViewMovie
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.*
import java.util.concurrent.atomic.AtomicInteger

class MovieRepositoryTest {

    @InjectMocks
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var privateDataSource: PrivateDataSource

    @Mock
    private lateinit var httpDataSource: HttpDataSource

    @Mock
    private lateinit var movieDao: MovieDao

    @Spy
    private lateinit var movieMapper: MovieMapper

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getMoviesLocalErrorTest() {
        // Arrange
        val error = Throwable()
        Mockito.`when`(movieDao.getMovies(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Single.error(error))
        // Act
        val testObserver = movieRepository.getMovies().test()

        // Asserts
        testObserver.assertError(error)
    }

    @Test
    fun getMoviesRemoteErrorTest() {
        // Arrange
        val error = Throwable()
        Mockito.`when`(movieDao.getMovies(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Single.just(arrayListOf()))
        Mockito.`when`(httpDataSource.getListMovies(Mockito.anyInt(), Mockito.nullable(String::class.java))).thenReturn(Single.error(error))

        // Act
        val testObserver = movieRepository.getMovies().test()

        // Asserts
        testObserver.assertError(error)
    }

    @Test
    fun getMoviesLocalTest() {
        // Arrange
        val list = prepareList()
        Mockito.`when`(movieDao.getMovies(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Single.just(list))
        // Act
        val testObserver = movieRepository.getMovies().test()

        // Asserts
        list[0].posterPath = null
        val info = MovieListInfo(MovieRepository.UNKNOWN_COUNT_PAGE, list, 1)
        testObserver.assertValue(info)
        Mockito.verify(httpDataSource, Mockito.never()).getListMovies(Mockito.anyInt(), Mockito.nullable(String::class.java))
        Mockito.verify(movieMapper).toMovieListInfo(MovieRepository.UNKNOWN_COUNT_PAGE, null, null, list, 1)
    }

    @Test
    fun getMoviesRemoteTest() {
        // Arrange
        val list = prepareList()
        val totalPage = 15
        val page = 1
        val count = AtomicInteger()
        Mockito.`when`(movieDao.getMovies(Mockito.anyInt(), Mockito.anyInt()))
                .thenAnswer {
                    if (count.get() == 0) {
                        count.incrementAndGet()
                        Single.just(arrayListOf())
                    } else {
                        Single.just(list)
                    }
                }
        Mockito.`when`(httpDataSource.getListMovies(Mockito.anyInt(), Mockito.nullable(String::class.java)))
                .thenReturn(Single.just(DiscoverResponse(page, 3000, totalPage, list)))
        // Act
        val testObserver = movieRepository.getMovies(page).test()


        // Asserts
        list[0].posterPath = null
        val info = MovieListInfo(totalPage, list, page)
        testObserver.assertValue(info)
        Mockito.verify(movieDao, Mockito.times(1)).save(list)
        Mockito.verify(movieDao, Mockito.times(2)).getMovies(Mockito.anyInt(), Mockito.anyInt())
        Mockito.verify(movieMapper).toMovieListInfo(totalPage, null, null, list, page)
    }

    @Test
    fun findMovieError() {
        // Arrange
        val error = Throwable()
        Mockito.`when`(httpDataSource.getMovie(Mockito.anyLong(), Mockito.nullable(String::class.java)))
                .thenReturn(Single.error(error))
        // Act
        val testObserver = movieRepository.findMovie(1).test()

        // Asserts
        testObserver.assertError(error)
    }

    @Test
    fun findMovie() {
        // Arrange
        val details = prepareDetails()
        Mockito.`when`(httpDataSource.getMovie(Mockito.anyLong(), Mockito.nullable(String::class.java)))
                .thenReturn(Single.just(details))
        // Act
        val testObserver = movieRepository.findMovie(1).test()

        // Asserts
        testObserver.assertValue(ViewMovie(details.title, details.overview, null, details.releaseDate,
                details.runtime, details.releaseDate))
        Mockito.verify(movieMapper).toViewMovie(details, null, null)
    }

    private fun prepareList(): MutableList<Movie> {
        val movie = Movie(1, 1, "test", false, "test",
                "test", listOf(1, 2, 3), "test", "test",
                "test", "test", 0.0, 1, false, 0.2)
        return arrayListOf(movie)
    }

    private fun prepareDetails(): DetailsMovie {
        return DetailsMovie(1, 1, "test", false, "test",
                "test", listOf(1, 2, 3), "test", "test",
                "test", "test", 0.0, 1, false, "60", 0.2)
    }

}