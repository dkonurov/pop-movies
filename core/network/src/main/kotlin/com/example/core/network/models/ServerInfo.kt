package com.example.core.network.models

internal data class ServerInfo(
    val timeoutRead: Long,
    val timeoutWrite: Long,
    val connectTimeout: Long
)