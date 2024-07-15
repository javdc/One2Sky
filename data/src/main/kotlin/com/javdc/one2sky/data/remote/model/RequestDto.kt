package com.javdc.one2sky.data.remote.model

import com.google.gson.annotations.SerializedName

data class RequestDto(
    @SerializedName("query") val query: String? = null,
    @SerializedName("type") val type: String? = null,
)
