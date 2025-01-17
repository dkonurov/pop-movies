//package com.example.dmitry.grades.domain.repositories.movie
//
//import com.example.core.data.db.inteface.FavoriteDao
//import com.example.core.data.db.inteface.MovieDao
//import com.example.core.data.preferences.PrivateDataSource
//import com.example.core.models.LocalDetailsMovie
//import com.example.core.network.remote.HttpDataSource
//import com.example.dmitry.grades.domain.models.entity.Movie
//import com.example.dmitry.grades.domain.models.response.DiscoverResponse
//import io.reactivex.Single
//import org.junit.Before
//import org.junit.Test
//import org.mockito.InjectMocks
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.MockitoAnnotations
//import org.mockito.Spy
//import java.util.concurrent.atomic.AtomicInteger
//
//class MovieRepositoryTest {
//
//    @InjectMocks
//    private lateinit var movieRepository: com.example.details.domain.movie.MovieRepositoryImpl
//
//    @Mock
//    private lateinit var privateDataSource: PrivateDataSource
//
//    @Mock
//    private lateinit var httpDataSource: HttpDataSource
//
//    @Mock
//    private lateinit var movieDao: MovieDao
//
//    @Mock
//    private lateinit var favoriteDao: FavoriteDao
//
//    @Spy
//    private lateinit var movieMapper: com.example.details.domain.mappers.MovieMapper
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.initMocks(this)
//    }
//
//    @Test
//    fun getMoviesLocalErrorTest() {
//        // Arrange
//        val error = Throwable()
//        Mockito.`when`(movieDao.getMovies(Mockito.anyInt(), Mockito.anyInt()))
//            .thenReturn(Single.error(error))
//        // Act
//        val testObserver = movieRepository.getMovies().test()
//
//        // Asserts
//        testObserver.assertError(error)
//    }
//
//    @Test
//    fun getMoviesRemoteErrorTest() {
//        // Arrange
//        val error = Throwable()
//        Mockito.`when`(movieDao.getMovies(Mockito.anyInt(), Mockito.anyInt()))
//            .thenReturn(Single.just(arrayListOf()))
//        Mockito.`when`(
//            httpDataSource.getListMovies(
//                Mockito.anyInt(),
//                Mockito.nullable(String::class.java)
//            )
//        ).thenReturn(Single.error(error))
//
//        // Act
//        val testObserver = movieRepository.getMovies().test()
//
//        // Asserts
//        testObserver.assertError(error)
//    }
//
//    @Test
//    fun getMoviesLocalTest() {
//        // Arrange
//        val list = prepareList()
//        Mockito.`when`(movieDao.getMovies(Mockito.anyInt(), Mockito.anyInt()))
//            .thenReturn(Single.just(list))
//        // Act
//        val testObserver = movieRepository.getMovies().test()
//
//        // Asserts
//        list[0].posterPath = null
//        val info = MovieListInfo(com.example.details.domain.movie.MovieRepositoryImpl.UNKNOWN_COUNT_PAGE, list, 1)
//        testObserver.assertValue(info)
//        Mockito.verify(httpDataSource, Mockito.never())
//            .getListMovies(Mockito.anyInt(), Mockito.nullable(String::class.java))
//        Mockito.verify(movieMapper)
//            .toMovieListInfo(com.example.details.domain.movie.MovieRepositoryImpl.UNKNOWN_COUNT_PAGE, null, null, list, 1)
//    }
//
//    @Test
//    fun getMoviesRemoteTest() {
//        // Arrange
//        val list = prepareList()
//        val totalPage = 15
//        val page = 1
//        val count = AtomicInteger()
//        Mockito.`when`(movieDao.getMovies(Mockito.anyInt(), Mockito.anyInt()))
//            .thenAnswer {
//                if (count.get() == 0) {
//                    count.incrementAndGet()
//                    Single.just(arrayListOf())
//                } else {
//                    Single.just(list)
//                }
//            }
//        Mockito.`when`(
//            httpDataSource.getListMovies(
//                Mockito.anyInt(),
//                Mockito.nullable(String::class.java)
//            )
//        )
//            .thenReturn(Single.just(DiscoverResponse(page, 3000, totalPage, list)))
//        // Act
//        val testObserver = movieRepository.getMovies(page).test()
//
//        // Asserts
//        list[0].posterPath = null
//        val info = MovieListInfo(totalPage, list, page)
//        testObserver.assertValue(info)
//        Mockito.verify(movieDao, Mockito.times(1)).save(list)
//        Mockito.verify(movieDao, Mockito.times(1)).getMovies(Mockito.anyInt(), Mockito.anyInt())
//        Mockito.verify(movieMapper).toMovieListInfo(totalPage, null, null, list, page)
//    }
//
//    @Test
//    fun findMovieError() {
//        // Arrange
//        val error = Throwable()
//        Mockito.`when`(
//            httpDataSource.getDetailsMovie(
//                Mockito.anyLong(),
//                Mockito.nullable(String::class.java)
//            )
//        )
//            .thenReturn(Single.error(error))
//        // Act
//        val testObserver = movieRepository.findMovie(1).test()
//
//        // Asserts
//        testObserver.assertError(error)
//    }
//
//    @Test
//    fun findMovie() {
//        // Arrange
//        val details = prepareDetails()
//        Mockito.`when`(
//            httpDataSource.getDetailsMovie(
//                Mockito.anyLong(),
//                Mockito.nullable(String::class.java)
//            )
//        )
//            .thenReturn(Single.just(details))
//        // Act
//        val testObserver = movieRepository.findMovie(1).test()
//
//        // Asserts
//        testObserver.assertValue(
//            com.example.details.view.ViewMovie(
//                details.id, details.title, details.overview, null, details.releaseDate,
//                details.runtime, details.releaseDate, false
//            )
//        )
//        Mockito.verify(movieMapper).toViewMovie(details, null, null, false)
//    }
//
//    private fun prepareList(): MutableList<Movie> {
//        val movie = Movie(
//            1, 1, "test", false, "test",
//            "test", listOf(1, 2, 3), "test", "test",
//            "test", "test", 0.0, 1, false, 0.2
//        )
//        return arrayListOf(movie)
//    }
//
//    private fun prepareDetails(): LocalDetailsMovie {
//        return LocalDetailsMovie(
//            1, 1, "test", false, "test",
//            "test", listOf(1, 2, 3), "test", "test",
//            "test", "test", 0.0, 1, false, "60", 0.2
//        )
//    }
//}