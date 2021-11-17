package com.example.prayertimes.data.prayer_times.repository

import com.example.prayertimes.data.prayer_times.datasource.remote.PrayerTimesApiService
import com.example.prayertimes.data.prayer_times.util.toDomainModel
import com.example.prayertimes.domain.prayer_times.datainterfaces.PrayerTimesRepository
import com.example.prayertimes.domain.prayer_times.model.PrayerTimesModel
import javax.inject.Inject

class PrayerTimesRepositoryImpl @Inject constructor(
    private val apiService: PrayerTimesApiService
) : PrayerTimesRepository {

    override suspend fun getPrayerTimes(
        long: String,
        lat: String,
        date: String
    ): PrayerTimesModel {
        return apiService.getPrayerTimes(
            long,
            lat,
            date
        ).results.datetime[0].times.toDomainModel()
    }
}