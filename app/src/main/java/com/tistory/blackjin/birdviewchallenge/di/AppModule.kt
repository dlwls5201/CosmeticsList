package com.tistory.blackjin.birdviewchallenge.di

import com.tistory.blackjin.birdviewchallenge.BuildConfig
import com.tistory.blackjin.birdviewchallenge.util.AppSchedulerProvider
import com.tistory.blackjin.birdviewchallenge.util.SchedulersProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single(named("debug")) { BuildConfig.DEBUG }

    single<SchedulersProvider> { AppSchedulerProvider() }
}