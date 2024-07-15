package com.javdc.one2sky.data.remote.model

import com.google.gson.annotations.SerializedName

data class NearestAreaDto(
    @SerializedName("areaName") val areaName: List<StringValueDto>? = null,
    @SerializedName("country") val country: List<StringValueDto>? = null,
    @SerializedName("latitude") val latitude: String? = null,
    @SerializedName("longitude") val longitude: String? = null,
    @SerializedName("population") val population: String? = null,
    @SerializedName("region") val region: List<StringValueDto>? = null,
    @SerializedName("weatherUrl") val weatherUrl: List<StringValueDto>? = null,
)
