package com.example.prayertimes.domain.prayer_times.model

import com.google.gson.annotations.SerializedName

data class PrayerTimesModel(
    val fajr : String,
    val dhuhr : String,
    val asr : String,
    val maghrib : String,
    val isha : String,
)