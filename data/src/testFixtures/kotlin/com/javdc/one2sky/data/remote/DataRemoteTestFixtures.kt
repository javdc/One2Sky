package com.javdc.one2sky.data.remote

import com.javdc.one2sky.data.remote.model.AstronomyDto
import com.javdc.one2sky.data.remote.model.CurrentConditionDto
import com.javdc.one2sky.data.remote.model.NearestAreaDto
import com.javdc.one2sky.data.remote.model.RequestDto
import com.javdc.one2sky.data.remote.model.StringValueDto
import com.javdc.one2sky.data.remote.model.WeatherDto
import com.javdc.one2sky.data.remote.model.WttrResponseDto

val astronomyDto = AstronomyDto(
    moonIllumination = "5",
    moonPhase = "Waxing Crescent",
    moonrise = "09:24 AM",
    moonset = "11:53 PM",
    sunrise = "07:11 AM",
    sunset = "09:47 PM",
)

val weatherDto = WeatherDto(
    astronomy = listOf(astronomyDto),
    avgtempC = "24",
    avgtempF = "75",
    date = "2024-07-08",
    maxtempC = "34",
    maxtempF = "94",
    mintempC = "15",
    mintempF = "59",
    sunHour = "14.0",
    totalSnowCm = "0.0",
    uvIndex = "10",
)

val currentConditionDto = CurrentConditionDto(
    feelsLikeC = "31",
    feelsLikeF = "89",
    cloudcover = "0",
    humidity = "27",
    langEs = listOf(StringValueDto("Soleado")),
    localObsDateTime = "2024-07-08 08:48 PM",
    observationTime = "06:48 PM",
    precipInches = "0.0",
    precipMM = "0.0",
    pressure = "1012",
    pressureInches = "30",
    tempC = "31",
    tempF = "88",
    uvIndex = "6",
    visibility = "10",
    visibilityMiles = "6",
    weatherCode = "113",
    weatherDesc = listOf(StringValueDto("Sunny")),
    weatherIconUrl = listOf(StringValueDto("https://www.infolojo.es/img/RnadomGroupSquare.png")),
    winddir16Point = "SW",
    winddirDegree = "220",
    windspeedKmph = "19",
    windspeedMiles = "12",
)

val requestDto = RequestDto(
    query = "Lat 37.39 and Lon -6.00",
    type = "LatLon",
)

val nearestAreaDto = NearestAreaDto(
    areaName = listOf(StringValueDto("Sevilla")),
    country = listOf(StringValueDto("Spain")),
    latitude = "37.377",
    longitude = "-5.987",
    population = "686647",
    region = listOf(StringValueDto("Andalucia")),
    weatherUrl = listOf(StringValueDto("https://www.infolojo.es/img/RnadomGroupSquare.png")),
)

val wttrResponseDto = WttrResponseDto(
    currentCondition = listOf(currentConditionDto),
    nearestArea = listOf(nearestAreaDto),
    request = listOf(requestDto),
    weather = listOf(weatherDto, weatherDto, weatherDto),
)
