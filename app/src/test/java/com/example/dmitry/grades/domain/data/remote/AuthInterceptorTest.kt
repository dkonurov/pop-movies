package com.example.dmitry.grades.domain.data.remote

import junit.framework.Assert
import okhttp3.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.MockitoAnnotations

class AuthInterceptorTest {

    private val interceptor = AuthInterceptor("test")

    private val mediaType = MediaType.parse("application/json")


    @Mock
    private lateinit var chain: Interceptor.Chain


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun postInterceptTest() {
        // Arrange
        val testUrl = "http://test.ru"
        val request = Request.Builder().post(RequestBody.create(mediaType, "{}"))
                .url(HttpUrl.get(testUrl)).build()
        val response = Response.Builder().request(request)
                .protocol(Protocol.HTTP_1_0).message("any").code(200).build()
        Mockito.`when`(chain.request()).thenReturn(request)
        Mockito.`when`(chain.proceed(any(Request::class.java))).thenReturn(response)

        // Act
        interceptor.intercept(chain)

        // Asserts
        Mockito.verify(chain).proceed(request)
    }

    @Test
    fun getInterceptTest() {
        // Arrange
        val testUrl = "http://test.ru"
        val argument = ArgumentCaptor.forClass(Request::class.java)
        val request = Request.Builder().get()
                .url(HttpUrl.get(testUrl)).build()
        val response = Response.Builder().request(request)
                .protocol(Protocol.HTTP_1_0).message("any").code(200).build()
        Mockito.`when`(chain.request()).thenReturn(request)
        Mockito.`when`(chain.proceed(any(Request::class.java))).thenReturn(response)

        // Act
        interceptor.intercept(chain)

        // Asserts
        Mockito.verify(chain).proceed(argument.capture())
        val query = argument.value.url().queryParameter(AuthInterceptor.API_KEY)
        Assert.assertEquals(query, "test")
    }
}