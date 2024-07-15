package com.javdc.one2sky.domain

import com.javdc.one2sky.domain.model.ForecastBo
import com.javdc.one2sky.domain.model.LocationBo
import com.javdc.one2sky.domain.model.WeatherBo
import java.time.LocalDate
import java.time.LocalDateTime

val locationBo = LocationBo(
    query = "sevilla",
    areaName = "Sevilla",
    region = "Andalucía",
)

val forecastBo = ForecastBo(
    date = LocalDate.of(2024, 7, 8),
    minTempCelsius = 34,
    maxTempCelsius = 15,
)

val weatherBo = WeatherBo(
    location = locationBo,
    observationDateTime = LocalDateTime.of(2024, 7, 8, 20, 48),
    description = "Sunny",
    descriptionEs = "Soleado",
    tempCelsius = 31,
    feelsLikeTempCelsius = 31,
    humidity = 27,
    windSpeedKmh = 19,
    forecast = listOf(forecastBo, forecastBo, forecastBo)
)
