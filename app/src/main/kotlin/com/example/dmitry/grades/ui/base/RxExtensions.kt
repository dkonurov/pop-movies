package com.example.dmitry.grades.ui.base

import com.example.dmitry.grades.domain.schedulers.SchedulerProvider
import io.reactivex.Single

fun <T> Single<T>.loading(loading: (Boolean) -> Unit): Single<T> {
    return compose { it ->
        it.doOnSubscribe {
            loading(true)
        }.doOnSuccess {
            loading(false)
        }.doOnError {
            loading(false)
        }
    }
}

fun <T> Single<T>.async(schedulerProvider: SchedulerProvider): Single<T> {
    return subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}