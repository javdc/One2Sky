package com.javdc.one2sky.data.repository

import com.javdc.one2sky.data.remote.datasource.WeatherRemoteDataSource
import com.javdc.one2sky.data.repository.util.RepositoryErrorManager
import com.javdc.one2sky.domain.model.WeatherBo
import com.javdc.one2sky.domain.repository.WeatherRepository
import com.javdc.one2sky.common.AsyncResult
import kotlinx.coroutines.flow.Flow

class WeatherRepositoryImpl(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
): WeatherRepository {

    override fun getWeather(): Flow<AsyncResult<WeatherBo>> =
        RepositoryErrorManager.wrap {
            weatherRemoteDataSource.getWeather()
        }

    override fun getWeatherForQuery(query: String): Flow<AsyncResult<WeatherBo>> =
        RepositoryErrorManager.wrap {
            weatherRemoteDataSource.getWeatherForQuery(query)
        }

}
