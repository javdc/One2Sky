package com.javdc.one2sky.domain.model

import java.time.LocalDate

data class ForecastBo(
    val date: LocalDate,
    val minTempCelsius: Int?,
    val maxTempCelsius: Int?,
)
