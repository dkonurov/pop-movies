package com.example.dmitry.grades.di.providers

import com.example.core.containers.createHttpDataSource
import com.example.core.data.remote.HttpDataSource
import com.example.core.models.config.ServerInfo
import com.example.dmitry.grades.di.ApiKey
import com.example.dmitry.grades.di.BaseUrl
import javax.inject.Inject
import javax.inject.Provider

class HttpDataSourceProvider @Inject constructor(
    @BaseUrl private val url: String,
    @ApiKey private val apiKey: String,
    private val serverInfo: ServerInfo
) : Provider<HttpDataSource> {
    override fun get() = createHttpDataSource(url, apiKey, serverInfo)
}