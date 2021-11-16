package com.example.prayertimes.domain.prayer_times.datainterfaces

import com.example.prayertimes.domain.prayer_times.model.PrayerTimesModel
import kotlinx.coroutines.flow.Flow

interface PrayerTimesRepository {

    suspend fun getPrayerTimes(long: String,lat:String,date:String):Flow<PrayerTimesModel>
}