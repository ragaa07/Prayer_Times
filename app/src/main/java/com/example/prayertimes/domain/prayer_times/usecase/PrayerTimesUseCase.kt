package com.example.prayertimes.domain.prayer_times.usecase

import com.example.prayertimes.domain.prayer_times.datainterfaces.PrayerTimesRepository
import com.example.prayertimes.domain.prayer_times.model.PrayerTimesModel
import com.example.prayertimes.domain.prayer_times.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PrayerTimesUseCase @Inject constructor(
    private val prayerTimesRepository: PrayerTimesRepository
) {

    fun getPrayerTimes(
        long: String,
        lat: String,
        date: String
    ): Flow<Resource<PrayerTimesModel>> = flow {
        try {
            emit(Resource.Loading())
            val times = prayerTimesRepository.getPrayerTimes(long, lat, date)
            emit(Resource.Success(times))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred !"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server, Check your network "))
        }
    }.flowOn(Dispatchers.IO)
}