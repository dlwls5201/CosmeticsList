package com.tistory.blackjin.birdviewchallenge.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: String,
    @SerializedName("oily_score")
    val oilyScore: Int,
    @SerializedName("dry_score")
    val dryScore: Int,
    @SerializedName("sensitive_score")
    val sensitiveScore: Int,
    @SerializedName("thumbnail_image")
    val thumbnailImage: String,
    @SerializedName("title")
    val title: String
)