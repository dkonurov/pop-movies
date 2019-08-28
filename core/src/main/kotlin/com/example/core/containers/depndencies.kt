package com.example.core.containers

import android.content.Context
import androidx.room.Room
import com.example.core.data.db.AppDatabase
import com.example.core.data.db.inteface.IDatabase
import com.example.core.data.preferences.PrivateDataSource
import com.example.core.data.preferences.PrivateDataSourceImpl
import com.example.core.data.remote.AuthInterceptor
import com.example.core.data.remote.HttpDataSource
import com.example.core.models.config.ServerInfo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createDatabase(context: Context, dbName: String): IDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()
}

fun createHttpDataSource(url: String, apiKey: String, serverInfo: ServerInfo): HttpDataSource {
    val retrofit = provideRetrofit(url, apiKey, serverInfo)
    return retrofit.create(HttpDataSource::class.java)
}

private fun provideOkhttpClient(key: String, serverInfo: ServerInfo): OkHttpClient {
    val httpLogger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(key))
            .addInterceptor(httpLogger)
            .connectTimeout(serverInfo.connectTimeout, TimeUnit.SECONDS)
            .writeTimeout(serverInfo.timeoutWrite, TimeUnit.SECONDS)
            .readTimeout(serverInfo.timeoutRead, TimeUnit.SECONDS)
            .build()
}

private fun provideRetrofit(url: String, key: String, serverInfo: ServerInfo) = Retrofit.Builder()
        .baseUrl(url)
        .client(provideOkhttpClient(key, serverInfo))
        .addConverterFactory(GsonConverterFactory.create(ContainerDI.gson))
        .build()

fun createPrivateDataSource(context: Context): PrivateDataSource = PrivateDataSourceImpl(context)