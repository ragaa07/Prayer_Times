package com.example.prayertimes.presentation.prayer_times

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.toArgb
import com.example.prayertimes.presentation.ui.theme.Blue900
import com.example.prayertimes.presentation.ui.theme.PrayerTimesTheme
import com.example.prayertimes.presentation.util.changeStatusBarColor
import com.example.prayertimes.presentation.util.formatDate
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: PrayerTimesViewModel by viewModels()
    private var lat: String? = null
    private var long: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        changeStatusBarColor(Blue900.toArgb())
        /* starts before 1 month from now */
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -1)
        /* ends after 1 month from now */
        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)

        super.onCreate(savedInstanceState)
        viewModel.getPrayerTimes("31.233334", "30.033333", Calendar.getInstance().formatDate())
        setContent {
            PrayerTimesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PrayerTimesScreen(viewModel = viewModel, startDate, endDate)
                }
            }
        }
    }
}


