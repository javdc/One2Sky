package com.javdc.one2sky.data.remote.model

import com.google.gson.annotations.SerializedName

data class StringValueDto(
    @SerializedName("value") val value: String? = null,
)
