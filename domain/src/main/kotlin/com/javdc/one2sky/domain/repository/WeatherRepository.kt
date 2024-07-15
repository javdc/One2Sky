package com.javdc.one2sky.domain.repository

import com.javdc.one2sky.domain.model.WeatherBo
import com.javdc.one2sky.common.AsyncResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeather(): Flow<AsyncResult<WeatherBo>>
    fun getWeatherForQuery(query: String): Flow<AsyncResult<WeatherBo>>
}
