package com.example.prayertimes.presentation.prayer_times

import com.example.prayertimes.domain.prayer_times.model.PrayerTimesModel

data class PrayerTimesUiState(
    val loading: Boolean = false,
    val times: PrayerTimesModel? = null,
    val error: String? = null
)
