package com.javdc.one2sky.data.remote.datasource

import com.javdc.one2sky.data.remote.api.WeatherService
import com.javdc.one2sky.data.remote.mapper.toWeatherBo
import com.javdc.one2sky.domain.model.WeatherBo

interface WeatherRemoteDataSource {
    suspend fun getWeather(): WeatherBo
    suspend fun getWeatherForQuery(query: String): WeatherBo
}

class WeatherRemoteDataSourceImpl(
    private val weatherService: WeatherService
) : WeatherRemoteDataSource {

    override suspend fun getWeather(): WeatherBo =
        weatherService
            .getWeather()
            .toWeatherBo()

    override suspend fun getWeatherForQuery(query: String): WeatherBo =
        weatherService
            .getWeatherForQuery(query)
            .toWeatherBo(query)

}
