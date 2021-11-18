package com.example.prayertimes.presentation.util

import android.app.Activity
import android.view.Window
import java.text.SimpleDateFormat
import java.util.*


/**
 * This extension is used for change the status bar color to a specific color
 * It used inside the onCreate method in the activity that you want to change  it's status bar color
 */
fun Activity.changeStatusBarColor(
    color: Int
) {
    val window: Window = this.window
    window.statusBarColor = color
}

/**
 * This extension is used format the date to ( yyyy-MM-dd ) pattern
 */
fun Calendar.formatDate(): String {
    return SimpleDateFormat("yyyy-MM-dd").format(this.time)
}






