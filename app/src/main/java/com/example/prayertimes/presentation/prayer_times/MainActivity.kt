package com.example.prayertimes.presentation.prayer_times

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.prayertimes.R
import com.example.prayertimes.presentation.ui.theme.Blue900
import com.example.prayertimes.presentation.ui.theme.PrayerTimesTheme
import com.example.prayertimes.presentation.util.Constant.LOCATION_UPDATE_INTERVAL
import com.example.prayertimes.presentation.util.changeStatusBarColor
import com.example.prayertimes.presentation.util.formatDate
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: PrayerTimesViewModel by viewModels()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var lat: String? by mutableStateOf(null)
    private var long: String? by mutableStateOf(null)
    private var gpsIsOf by mutableStateOf(false)

    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarColor(Blue900.toArgb())
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        initLocationCallBack()
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -1)
        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)

        setContent {
            val permission = rememberMultiplePermissionsState(
                permissions = listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(
                key1 = lifecycleOwner,
                effect = {
                    val observer = LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_START) {
                            permission.launchMultiplePermissionRequest()
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)

                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                }
            )
            if (permission.allPermissionsGranted) {
                startLocationUpdates()
                getLocation()
            }

            PrayerTimesTheme {
                Surface(color = MaterialTheme.colors.background) {
                    if (gpsIsOf) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = {
                                getLocation()
                            }) {
                                Text(
                                    text = getString(R.string.try_again_button_text),
                                    color = Color.White
                                )
                            }
                        }

                    } else {
                        lat?.let {
                            long?.let { it1 ->
                                PrayerTimesScreen(
                                    viewModel = viewModel,
                                    calenderStartDate = startDate,
                                    calenderEndDate = endDate,
                                    lat = it,
                                    long = it1
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    private fun getLocation() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->

                    val location: Location? = task.result
                    if (location != null) {
                        gpsIsOf = false
                        // the api doesn't support damita coordinates, so this is a temporarily solution.
                        long = location.longitude.toString()
                        lat = location.latitude.toString()
                        getPrayerTimes()
                    } else {
                        gpsIsOf = true
                        Toast.makeText(
                            this,
                            getString(R.string.gps_is_of_message),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun getPrayerTimes() {
        if (lat != null && long != null) {
            viewModel.getPrayerTimes(long!!, lat!!, Calendar.getInstance().formatDate())
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest().apply {
            interval = LOCATION_UPDATE_INTERVAL
            priority = PRIORITY_HIGH_ACCURACY
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun initLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    if (location != null) {
                        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                    }
                }
            }
        }
    }
}


