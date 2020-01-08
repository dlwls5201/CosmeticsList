package com.tistory.blackjin.birdviewchallenge.di

import com.tistory.blackjin.birdviewchallenge.data.ProductRepository
import org.koin.dsl.module

val repoModule = module {

    single {
        ProductRepository(
            get(),
            get()
        )
    }
}