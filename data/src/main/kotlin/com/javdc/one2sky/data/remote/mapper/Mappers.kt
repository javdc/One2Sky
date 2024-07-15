package com.javdc.one2sky.data.remote.mapper

import com.javdc.one2sky.common.util.serverDateFormatter
import com.javdc.one2sky.common.util.serverDateTimeFormatter
import com.javdc.one2sky.common.util.tryOrNull
import com.javdc.one2sky.data.remote.model.NearestAreaDto
import com.javdc.one2sky.data.remote.model.WeatherDto
import com.javdc.one2sky.data.remote.model.WttrResponseDto
import com.javdc.one2sky.data.remote.util.value
import com.javdc.one2sky.domain.model.ForecastBo
import com.javdc.one2sky.domain.model.LocationBo
import com.javdc.one2sky.domain.model.WeatherBo
import java.time.LocalDate
import java.time.LocalDateTime

fun WttrResponseDto.toWeatherBo(query: String = ""): WeatherBo {
    val currentCondition = currentCondition?.firstOrNull()
    val nearestArea = nearestArea?.firstOrNull()
    return WeatherBo(
        location = nearestArea?.toLocationBo(query),
        observationDateTime = currentCondition?.localObsDateTime?.let {
            tryOrNull {
                LocalDateTime.parse(it, serverDateTimeFormatter)
            }
        },
        description = currentCondition?.weatherDesc?.value,
        descriptionEs = currentCondition?.langEs?.value,
        tempCelsius = currentCondition?.tempC?.toIntOrNull(),
        feelsLikeTempCelsius = currentCondition?.feelsLikeC?.toIntOrNull(),
        humidity = currentCondition?.humidity?.toIntOrNull(),
        windSpeedKmh = currentCondition?.windspeedKmph?.toIntOrNull(),
        forecast = weather?.mapNotNull { it.toForecastBo() },
    )
}

fun NearestAreaDto.toLocationBo(query: String = "") = LocationBo(
    query = query,
    areaName = areaName.value,
    region = region.value,
)

fun WeatherDto.toForecastBo() = date?.let { date ->
    tryOrNull {
        ForecastBo(
            date = LocalDate.parse(date, serverDateFormatter),
            minTempCelsius = mintempC?.toIntOrNull(),
            maxTempCelsius = maxtempC?.toIntOrNull(),
        )
    }
}
