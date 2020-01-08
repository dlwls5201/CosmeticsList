package com.tistory.blackjin.birdviewchallenge.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object DeviceUtil {

    fun getDeviceWidth(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        return displayMetrics.widthPixels
    }
}