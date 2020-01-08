package com.tistory.blackjin.birdviewchallenge.util.extension

import android.content.Context
import android.util.TypedValue

fun Int.toDp(context : Context) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics)