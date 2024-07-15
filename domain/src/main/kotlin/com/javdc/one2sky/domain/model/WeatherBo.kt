package com.javdc.one2sky.domain.model

import java.time.LocalDateTime

data class WeatherBo(
    val location: LocationBo?,
    val observationDateTime: LocalDateTime?,
    val description: String?,
    val descriptionEs: String?,
    val tempCelsius: Int?,
    val feelsLikeTempCelsius: Int?,
    val humidity: Int?,
    val windSpeedKmh: Int?,
    val forecast: List<ForecastBo>?,
)
