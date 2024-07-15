package com.javdc.one2sky.domain.usecase

import com.javdc.one2sky.domain.model.WeatherBo
import com.javdc.one2sky.domain.repository.WeatherRepository
import com.javdc.one2sky.common.AsyncResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

interface GetWeatherUseCase {
    operator fun invoke(query: String?): Flow<AsyncResult<WeatherBo>>
}

class GetWeatherUseCaseImpl(
    private val weatherRepository: WeatherRepository,
    private val getFavoriteQueryUseCase: GetFavoriteQueryUseCase,
) : GetWeatherUseCase {

    override operator fun invoke(query: String?): Flow<AsyncResult<WeatherBo>> {
        return flow {
            val queryToUse = query ?: getFavoriteQueryUseCase().first()
            queryToUse?.let {
                weatherRepository.getWeatherForQuery(it).collect {
                    emit(it)
                }
            } ?: run {
                weatherRepository.getWeather().collect {
                    emit(it)
                }
            }
        }
    }

}
