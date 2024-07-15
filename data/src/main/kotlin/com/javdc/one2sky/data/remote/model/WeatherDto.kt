package com.javdc.one2sky.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("astronomy") val astronomy: List<AstronomyDto>? = listOf(),
    @SerializedName("avgtempC") val avgtempC: String? = null,
    @SerializedName("avgtempF") val avgtempF: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("maxtempC") val maxtempC: String? = null,
    @SerializedName("maxtempF") val maxtempF: String? = null,
    @SerializedName("mintempC") val mintempC: String? = null,
    @SerializedName("mintempF") val mintempF: String? = null,
    @SerializedName("sunHour") val sunHour: String? = null,
    @SerializedName("totalSnow_cm") val totalSnowCm: String? = null,
    @SerializedName("uvIndex") val uvIndex: String? = null,
)
