package com.example.prayertimes.presentation.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.view.Window
import androidx.core.content.ContextCompat
import com.example.prayertimes.presentation.util.Constant.REQUEST_CODE_LOCATION_PERMISSION
import pub.devrel.easypermissions.EasyPermissions
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






