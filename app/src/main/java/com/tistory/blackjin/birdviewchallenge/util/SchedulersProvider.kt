package com.tistory.blackjin.birdviewchallenge.util

import io.reactivex.Scheduler

interface SchedulersProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
}
