package com.example.core.network.model

internal data class ServerInfo(
    val timeoutRead: Long,
    val timeoutWrite: Long,
    val connectTimeout: Long
)