package com.tistory.blackjin.birdviewchallenge

import android.app.Application
import com.tistory.blackjin.birdviewchallenge.di.appModule
import com.tistory.blackjin.birdviewchallenge.di.networkModule
import com.tistory.blackjin.birdviewchallenge.di.repoModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import timber.log.Timber

class BirdviewApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupTimber()
        setupKoin()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupKoin() {
        startKoin {
            logger(
                if (BuildConfig.DEBUG) {
                    AndroidLogger()
                } else {
                    EmptyLogger()
                }
            )

            androidContext(this@BirdviewApplication)

            modules(
                listOf(
                    appModule, networkModule, repoModule
                )
            )
        }
    }
}