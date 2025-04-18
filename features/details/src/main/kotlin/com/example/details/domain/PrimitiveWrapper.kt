package com.example.details.domain

internal data class PrimitiveWrapper<out T>(
    val value: T,
)