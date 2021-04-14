package com.gilbertparreno.cartrack.core.utils

import android.content.res.Resources

object ScreenHelper {

    private var screenWidth: Int = 0
    private var screenHeight: Int = 0

    fun getStatusBarHeightFromAndroid(): Int {
        var statusBarHeight = 0
        val resourceId =
            Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = Resources.getSystem().getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }
}