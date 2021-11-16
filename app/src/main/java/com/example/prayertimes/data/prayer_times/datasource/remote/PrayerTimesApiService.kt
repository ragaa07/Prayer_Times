package com.example.prayertimes.data.prayer_times.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface PrayerTimesApiService {

    @GET("times/day.json")
   suspend fun getPrayerTimes(
        @Query("longitude") long: String,
        @Query("latitude") lat: String,
        @Query("date") date: String
    ):PrayerTimesResponse
}