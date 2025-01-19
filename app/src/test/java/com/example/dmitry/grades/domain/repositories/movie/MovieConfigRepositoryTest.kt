//package com.example.dmitry.grades.domain.repositories.movie
//
//import com.example.core.data.preferences.PrivateDataSource
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.InjectMocks
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.junit.MockitoJUnitRunner
//
//@RunWith(MockitoJUnitRunner::class)
//class MovieConfigRepositoryTest {
//
//    @Mock
//    private lateinit var privateDataSource: PrivateDataSource
//
//    @InjectMocks
//    private lateinit var movieConfigRepository: MovieConfigRepository
//
//    @Test
//    fun testSortByGetTest() {
//        // Act
//        movieConfigRepository.sortBy
//
//        // Assert
//        Mockito.verify(privateDataSource).sortBy
//    }
//
//    @Test
//    fun testSortBySetTest() {
//        // Act
//        movieConfigRepository.sortBy = "test"
//
//        // Assert
//        Mockito.verify(privateDataSource).sortBy = "test"
//    }
//}