package com.tistory.blackjin.birdviewchallenge.data.error

class MyHttpException(val code: Int, override val message: String) : Throwable()
