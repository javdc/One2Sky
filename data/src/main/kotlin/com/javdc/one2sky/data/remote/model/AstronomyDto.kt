package com.javdc.one2sky.data.remote.model

import com.google.gson.annotations.SerializedName

data class AstronomyDto(
    @SerializedName("moon_illumination") val moonIllumination: String? = null,
    @SerializedName("moon_phase") val moonPhase: String? = null,
    @SerializedName("moonrise") val moonrise: String? = null,
    @SerializedName("moonset") val moonset: String? = null,
    @SerializedName("sunrise") val sunrise: String? = null,
    @SerializedName("sunset") val sunset: String? = null,
)
