package com.tistory.blackjin.birdviewchallenge.data.model

import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("statusCode") val statusCode: Boolean,
    @SerializedName("body") val body: T
)
