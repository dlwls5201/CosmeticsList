package com.tistory.blackjin.birdviewchallenge.di

import com.tistory.blackjin.birdviewchallenge.constant.NetworkService
import com.tistory.blackjin.birdviewchallenge.data.source.remote.ProductService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        get<Retrofit>().create(ProductService::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl(NetworkService.BASE_URL)
            .client(get())
            .addCallAdapterFactory(get())
            .addConverterFactory(get())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder().apply {
            // 이 클라이언트를 통해 오고 가는 네트워크 요청/응답을 로그로 표시하도록 합니다.
            addInterceptor(get())
        }.build()
    }

    single<okhttp3.Interceptor> {

        val debug: Boolean by inject(named("debug"))

        HttpLoggingInterceptor().apply {
            level = if (debug) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single<CallAdapter.Factory> {
        RxJava2CallAdapterFactory.createAsync()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create()
    }
}