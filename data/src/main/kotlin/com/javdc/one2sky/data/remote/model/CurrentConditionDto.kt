package com.javdc.one2sky.data.remote.model

import com.google.gson.annotations.SerializedName

data class CurrentConditionDto(
    @SerializedName("FeelsLikeC") val feelsLikeC: String? = null,
    @SerializedName("FeelsLikeF") val feelsLikeF: String? = null,
    @SerializedName("cloudcover") val cloudcover: String? = null,
    @SerializedName("humidity") val humidity: String? = null,
    @SerializedName("lang_es") val langEs: List<StringValueDto>? = null,
    @SerializedName("localObsDateTime") val localObsDateTime: String? = null,
    @SerializedName("observation_time") val observationTime: String? = null,
    @SerializedName("precipInches") val precipInches: String? = null,
    @SerializedName("precipMM") val precipMM: String? = null,
    @SerializedName("pressure") val pressure: String? = null,
    @SerializedName("pressureInches") val pressureInches: String? = null,
    @SerializedName("temp_C") val tempC: String? = null,
    @SerializedName("temp_F") val tempF: String? = null,
    @SerializedName("uvIndex") val uvIndex: String? = null,
    @SerializedName("visibility") val visibility: String? = null,
    @SerializedName("visibilityMiles") val visibilityMiles: String? = null,
    @SerializedName("weatherCode") val weatherCode: String? = null,
    @SerializedName("weatherDesc") val weatherDesc: List<StringValueDto>? = null,
    @SerializedName("weatherIconUrl") val weatherIconUrl: List<StringValueDto>? = null,
    @SerializedName("winddir16Point") val winddir16Point: String? = null,
    @SerializedName("winddirDegree") val winddirDegree: String? = null,
    @SerializedName("windspeedKmph") val windspeedKmph: String? = null,
    @SerializedName("windspeedMiles") val windspeedMiles: String? = null,
)
