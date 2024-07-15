package com.javdc.one2sky.domain.usecase

import com.javdc.one2sky.domain.model.WeatherBo
import com.javdc.one2sky.domain.repository.WeatherRepository
import com.javdc.one2sky.common.AsyncResult
import kotlinx.coroutines.flow.Flow

interface GetWeatherForQueryUseCase {
    operator fun invoke(query: String): Flow<AsyncResult<WeatherBo>>
}

class GetWeatherForQueryUseCaseImpl(
    private val weatherRepository: WeatherRepository,
) : GetWeatherForQueryUseCase {

    override operator fun invoke(query: String): Flow<AsyncResult<WeatherBo>> {
        return weatherRepository.getWeatherForQuery(query)
    }

}
