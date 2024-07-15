package com.javdc.one2sky.data.remote.model

import com.google.gson.annotations.SerializedName

data class WttrResponseDto(
    @SerializedName("current_condition") val currentCondition: List<CurrentConditionDto>? = listOf(),
    @SerializedName("nearest_area") val nearestArea: List<NearestAreaDto>? = listOf(),
    @SerializedName("request") val request: List<RequestDto>? = listOf(),
    @SerializedName("weather") val weather: List<WeatherDto>? = listOf(),
)
