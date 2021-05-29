package com.example.core.di.scope;

import javax.inject.Scope;

import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;

@Scope
@Retention(AnnotationRetention.RUNTIME)
public @interface CoreScope {
}
