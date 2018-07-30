package com.example.dmitry.grades.domain.models.config

data class ServerInfo(val timeoutRead: Long,
                      val timeoutWrite: Long,
                      val connectTimeout: Long)