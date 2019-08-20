package com.example.dmitry.grades.di.providers

import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.SupervisorJob
import javax.inject.Provider

class SupervisorProvider : Provider<CompletableJob> {
    override fun get(): CompletableJob = SupervisorJob()
}