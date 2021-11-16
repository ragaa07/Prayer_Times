package com.example.prayertimes.data.prayer_times.util

import com.example.prayertimes.data.prayer_times.datasource.remote.Times
import com.example.prayertimes.domain.prayer_times.model.PrayerTimesModel

fun Times.toDomainModel(): PrayerTimesModel {
    return PrayerTimesModel(
        fajr, dhuhr, asr, maghrib, isha
    )
}
