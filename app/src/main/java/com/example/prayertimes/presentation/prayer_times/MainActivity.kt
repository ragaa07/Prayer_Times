package com.example.prayertimes.presentation.prayer_times

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.prayertimes.presentation.ui.theme.PrayerTimesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: PrayerTimesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPrayerTimes("31.233334", "30.033333", "2021-11-18")
        setContent {
            PrayerTimesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel: PrayerTimesViewModel) {
    val state = viewModel.prayerTimesState.value
    if (state.times!=null) {
        Row() {
            Text(text = state.times.fajr)
            Text(text = state.times.dhuhr)
            Text(text = state.times.asr)
        }
    }
}

