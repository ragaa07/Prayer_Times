package com.example.prayertimes.di

import com.example.prayertimes.data.prayer_times.datasource.remote.PrayerTimesApiService
import com.example.prayertimes.data.prayer_times.repository.PrayerTimesRepositoryImpl
import com.example.prayertimes.data.prayer_times.util.Constants
import com.example.prayertimes.domain.prayer_times.datainterfaces.PrayerTimesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrayerTimesModule {

    @Provides
    @Singleton
    fun providePrayerTimesApi(): PrayerTimesApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PrayerTimesApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePrayerRepository(apiService: PrayerTimesApiService): PrayerTimesRepository {
        return PrayerTimesRepositoryImpl(apiService)
    }
}