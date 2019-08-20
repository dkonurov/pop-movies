package com.example.dmitry.grades.ui.splash

import androidx.lifecycle.Observer
import com.example.dmitry.grades.R
import com.example.dmitry.grades.domain.models.ImageConfig
import com.example.dmitry.grades.domain.repositories.ResourceRepository
import com.example.dmitry.grades.domain.repositories.movie.MovieRepositoryImpl
import com.example.dmitry.grades.domain.schedulers.SchedulerProvider
import com.example.dmitry.grades.ui.movie.MainActivity
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SplashViewModelTest {

    @Mock
    private lateinit var movieRepository: MovieRepositoryImpl

    @Mock
    private lateinit var resourceRepository: ResourceRepository

    @Mock
    private lateinit var configObserver: Observer<ImageConfig>

    @Mock
    private lateinit var toastObserver: Observer<String>

    @Mock
    private lateinit var progressObserver: Observer<Boolean>

    private val schedulerProvider = object : SchedulerProvider {
        override fun io(): Scheduler {
            return Schedulers.trampoline()
        }

        override fun ui(): Scheduler {
            return Schedulers.trampoline()
        }

    }

    private lateinit var splashViewModel: SplashViewModel

//    @Before
//    fun setup() {
//        MockitoAnnotations.initMocks(this)
//        splashViewModel = SplashViewModel(movieRepository, resourceRepository, schedulerProvider)
//        clearInvocations(configObserver, toastObserver, progressObserver)
//        splashViewModel.imageConfig.observeForever(configObserver)
//        splashViewModel.error.observeForever(toastObserver)
//        splashViewModel.loading.observeForever(progressObserver)
//    }
//
//    @Test
//    fun loadConfigErrorTest() {
//        // Arrange
//        val error = "error"
//        `when`(movieRepository.getConfiguration()).thenReturn(Single.error(Throwable()))
//        `when`(resourceRepository.getNetworkError()).thenReturn(error)
//
//        // Act
//        splashViewModel.loadConfig()
//
//        // Asserts
//        verify(toastObserver).onChanged(error)
//        verify(progressObserver).onChanged(true)
//        verify(progressObserver, atLeast(1)).onChanged(false)
//        verify(configObserver, never()).onChanged(any(ImageConfig::class.java))
//        verify(resourceRepository).getNetworkError()
//    }
//
//
//    @Test
//    fun loadConfigTest() {
//        // Arrange
//        val config = ImageConfig("test", listOf("1", "2", "3"), listOf("1", "2", "3"))
//        `when`(movieRepository.getConfiguration()).thenReturn(Single.just(config))
//
//
//        // Act
//        splashViewModel.loadConfig()
//
//        // Asserts
//        verify(toastObserver, never()).onChanged(anyString())
//        verify(progressObserver).onChanged(true)
//        verify(progressObserver, atLeast(1)).onChanged(false)
//        verify(configObserver).onChanged(config)
//        verify(movieRepository).getConfiguration()
//    }
//
//    @Test
//    fun showedToastTest() {
//        // Arrange
//        val error = "error"
//        `when`(movieRepository.getConfiguration()).thenReturn(Single.error(Throwable()))
//        `when`(resourceRepository.getNetworkError()).thenReturn(error)
//        splashViewModel.loadConfig()
//        clearInvocations(toastObserver)
//
//        // Act
//        splashViewModel.showedToast()
//
//        // Asserts
//        verify(toastObserver).onChanged(null)
//
//
//    }

}