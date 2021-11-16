package com.example.prayertimes.data.prayer_times.datasource.remote

import com.google.gson.annotations.SerializedName

data class PrayerTimesResponse(
    @SerializedName("code") val code : Int,
    @SerializedName("status") val status : String,
    @SerializedName("results") val results : Results
)

data class Results(
    @SerializedName("datetime") val datetime : List<DateTime>,
    @SerializedName("location") val location : Location,
    @SerializedName("settings") val settings : Settings
)

data class DateTime(
    @SerializedName("times") val times : Times,
    @SerializedName("date") val date : Date
)

data class Date(
    @SerializedName("timestamp") val timestamp : Int,
    @SerializedName("gregorian") val gregorian : String,
    @SerializedName("hijri") val hijri : String
)

data class Times(

    @SerializedName("Imsak") val imsak : String,
    @SerializedName("Sunrise") val sunrise : String,
    @SerializedName("Fajr") val fajr : String,
    @SerializedName("Dhuhr") val dhuhr : String,
    @SerializedName("Asr") val asr : String,
    @SerializedName("Sunset") val sunset : String,
    @SerializedName("Maghrib") val maghrib : String,
    @SerializedName("Isha") val isha : String,
    @SerializedName("Midnight") val midnight : String
)

data class Location(

    @SerializedName("latitude") val latitude : Double,
    @SerializedName("longitude") val longitude : Double,
    @SerializedName("elevation") val elevation : Int,
    @SerializedName("country") val country : String,
    @SerializedName("country_code") val country_code : String,
    @SerializedName("timezone") val timezone : String,
    @SerializedName("local_offset") val local_offset : Int
)

data class Settings(
    @SerializedName("timeformat") val timeformat : String,
    @SerializedName("school") val school : String,
    @SerializedName("juristic") val juristic : String,
    @SerializedName("highlat") val highlat : String,
    @SerializedName("fajr_angle") val fajr_angle : Int,
    @SerializedName("isha_angle") val isha_angle : Int
)