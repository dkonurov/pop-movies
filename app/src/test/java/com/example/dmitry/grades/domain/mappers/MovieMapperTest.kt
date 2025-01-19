//package com.example.dmitry.grades.domain.mappers
//
//import com.example.core.models.LocalDetailsMovie
//import com.example.dmitry.grades.domain.models.entity.Movie
//import junit.framework.Assert
//import org.junit.Test
//
//class MovieMapperTest {
//
//    private val movieMapper = com.example.details.domain.mappers.MovieMapper()
//
//    @Test
//    fun toMovieListNullPosterTest() {
//        // Arrange
//        val movies = prepareList()
//        movies.forEach {
//            it.posterPath = null
//        }
//
//        // Act
//        val list = movieMapper.toMovieListInfo(10, "test1", "test2", movies, 2)
//
//        // Asserts
//        list.movies.forEach {
//            Assert.assertNull(it.posterPath)
//        }
//    }
//
//    @Test
//    fun toMovieListNullPathTest() {
//        // Arrange
//        val movies = prepareList()
//
//        // Act
//        val list = movieMapper.toMovieListInfo(10, null, "test2", movies, 2)
//
//        // Asserts
//        list.movies.forEach {
//            Assert.assertNull(it.posterPath)
//        }
//    }
//
//    @Test
//    fun toMovieListNullSizeTest() {
//        // Arrange
//        val movies = prepareList()
//
//        // Act
//        val list = movieMapper.toMovieListInfo(10, "test1", null, movies, 2)
//
//        // Asserts
//        list.movies.forEach {
//            Assert.assertNull(it.posterPath)
//        }
//    }
//
//    @Test
//    fun toMovieListTest() {
//        // Arrange
//        val movies = prepareList()
//
//        // Act
//        val list = movieMapper.toMovieListInfo(10, "test1", "test2", movies, 2)
//
//        // Asserts
//        list.movies.forEach {
//            Assert.assertEquals("test1test2test", it.posterPath)
//        }
//    }
//
//    @Test
//    fun toViewMovieNullPosterTest() {
//        // Arrange
//        val movie = prepareDetails().apply {
//            posterPath = null
//        }
//
//        // Act
//        val viewMovie = movieMapper.toViewMovie(movie, "test", "test", false)
//
//        // Asserts
//        Assert.assertNull(viewMovie.poster)
//    }
//
//    @Test
//    fun toViewMovieNullPathTest() {
//        // Arrange
//        val movie = prepareDetails()
//
//        // Act
//        val viewMovie = movieMapper.toViewMovie(movie, null, "test", false)
//
//        // Asserts
//        Assert.assertNull(viewMovie.poster)
//    }
//
//    @Test
//    fun toViewMovieNullSizeTest() {
//        // Arrange
//        val movie = prepareDetails()
//
//        // Act
//        val viewMovie = movieMapper.toViewMovie(movie, "test", null, false)
//
//        // Asserts
//        Assert.assertNull(viewMovie.poster)
//    }
//
//    @Test
//    fun toViewMovieYearMin4Test() {
//        // Arrange
//        val movie = prepareDetails()
//            .apply {
//                releaseDate = "a"
//            }
//
//        // Act
//        val viewMovie = movieMapper.toViewMovie(movie, "test1", "test2", false)
//
//        // Asserts
//        Assert.assertEquals(viewMovie.release, "a")
//    }
//
//    @Test
//    fun toViewMovieTest() {
//        // Arrange
//        val movie = prepareDetails()
//
//        // Act
//        val viewMovie = movieMapper.toViewMovie(movie, "test1", "test2", true)
//
//        // Asserts
//        Assert.assertEquals(viewMovie.release, movie.releaseDate)
//        Assert.assertEquals(viewMovie.about, movie.overview)
//        Assert.assertEquals(viewMovie.time, movie.runtime)
//        Assert.assertEquals(viewMovie.title, movie.title)
//        Assert.assertEquals(viewMovie.year, movie.releaseDate)
//        Assert.assertEquals(viewMovie.isFavorite, true)
//        Assert.assertEquals(viewMovie.poster, "test1test2test")
//    }
//
//    @Test
//    fun toFavoriteTest() {
//        // Arrange
//        val viewMovie = prepareViewMovie()
//
//        // Act
//        val favorite = movieMapper.toFavorite(viewMovie)
//
//        // Asserts
//        Assert.assertEquals(viewMovie.poster, favorite.poster)
//        Assert.assertEquals(viewMovie.title, favorite.title)
//        Assert.assertEquals(viewMovie.about, favorite.about)
//        Assert.assertEquals(viewMovie.release, favorite.release)
//        Assert.assertEquals(viewMovie.time, favorite.time)
//        Assert.assertEquals(viewMovie.id, favorite.id)
//    }
//
//    private fun prepareViewMovie(): com.example.details.view.ViewMovie {
//        return com.example.details.view.ViewMovie(
//            1,
//            "test",
//            "test",
//            null,
//            "test",
//            null,
//            "test",
//            false
//        )
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