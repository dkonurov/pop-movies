package com.example.core.model

data class PresentationError(
    val message: String,
    val throwable: Throwable
)