package com.example.core.models.config

data class ServerInfo(val timeoutRead: Long,
                      val timeoutWrite: Long,
                      val connectTimeout: Long)