package com.example.prayertimes.presentation.prayer_times

import android.view.LayoutInflater
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.prayertimes.R
import com.example.prayertimes.domain.prayer_times.model.PrayerTimesModel
import com.example.prayertimes.presentation.ui.theme.Blue900
import com.example.prayertimes.presentation.util.formatDate
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import java.util.*

@Composable
fun PrayerTimesScreen(
    viewModel: PrayerTimesViewModel,
    calenderStartDate: Calendar,
    calenderEndDate: Calendar,
    lat: String?,
    long: String?
) {
    val state = viewModel.prayerTimesState.value
    Card(backgroundColor = Color(Blue900.toArgb())) {
        Column(modifier = Modifier.fillMaxSize()) {
            CustomCalender(viewModel = viewModel, calenderStartDate, calenderEndDate, lat, long)
            Card(shape = RoundedCornerShape(25.dp)) {
                LoadingIndicator(isLoading = state.loading)
                PrayerTimesSection(times = state.times)
                ErrorMessages(message = state.error)
            }
        }
    }
}

@Composable
fun CustomCalender(
    viewModel: PrayerTimesViewModel,
    startDate: Calendar,
    endDate: Calendar,
    lat: String?,
    long: String?
) {
    AndroidView(factory = {
        val view = LayoutInflater.from(it)
            .inflate(R.layout.horizontal_calender, null, false)
        val horizontalCalender: HorizontalCalendar =
            HorizontalCalendar.Builder(view, R.id.calendarView)
                .range(startDate, endDate)
                .defaultSelectedDate(Calendar.getInstance())
                .datesNumberOnScreen(5)
                .build()
        horizontalCalender.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                if (date != null && lat != null && long != null) {
                    viewModel.getPrayerTimes(long, lat, date.formatDate())
                }
            }
        }
        view
    })
}

@Composable
fun LoadingIndicator(isLoading: Boolean) {
    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .rotate(-90f)
                    .size(100.dp)
                    .padding(15.dp),
                color = Color(Blue900.toArgb())
            )
        }
    }
}

@Composable
fun PrayerTimesSection(times: PrayerTimesModel?) {
    val context = LocalContext.current
    if (times != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp, vertical = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = context.getString(R.string.fajr),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(text = times.fajr, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = Color(Blue900.toArgb()), thickness = 1.dp)
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = context.getString(R.string.dhuhr),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(text = times.dhuhr, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = Color(Blue900.toArgb()), thickness = 1.dp)
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = context.getString(R.string.asr),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(text = times.asr, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = Color(Blue900.toArgb()), thickness = 1.dp)
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = context.getString(R.string.maghrib),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(text = times.maghrib, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = Color(Blue900.toArgb()), thickness = 1.dp)
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = context.getString(R.string.isha),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(text = times.isha, color = Color.Gray)
            }
        }
    }
}

@Composable
fun ErrorMessages(message: String?) {
    if (message != null) {
        Text(
            text = message,
            modifier = Modifier.fillMaxSize(),
            color = Blue900,
            textAlign = TextAlign.Center
        )
    }
}