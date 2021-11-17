package com.example.prayertimes.presentation.prayer_times

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayertimes.domain.prayer_times.usecase.PrayerTimesUseCase
import com.example.prayertimes.domain.prayer_times.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PrayerTimesViewModel @Inject constructor(
    private val useCase: PrayerTimesUseCase
) : ViewModel() {

    private val _prayerTimesState = mutableStateOf(PrayerTimesUiState())
    val prayerTimesState: State<PrayerTimesUiState> = _prayerTimesState

    fun getPrayerTimes(long: String, lat: String, date: String) {
        useCase.getPrayerTimes(long, lat, date).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _prayerTimesState.value = PrayerTimesUiState(loading = true)
                }
                is Resource.Error -> {
                    _prayerTimesState.value =
                        PrayerTimesUiState(error = result.message ?: "An unexpected Error occurred")
                }
                is Resource.Success -> {
                    _prayerTimesState.value = PrayerTimesUiState(times = result.data)
                }
            }
        }.launchIn(viewModelScope)

    }

}