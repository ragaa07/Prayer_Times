package com.example.prayertimes.domain.prayer_times.usecase

import com.example.prayertimes.domain.prayer_times.datainterfaces.PrayerTimesRepository
import com.example.prayertimes.domain.prayer_times.model.PrayerTimesModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PrayerTimesUseCase @Inject constructor (private val prayerTimesRepository: PrayerTimesRepository) {

   suspend fun getPrayerTimes(long:String,lat:String,date:String):Flow<PrayerTimesModel>
    = prayerTimesRepository.getPrayerTimes(long, lat, date)
}