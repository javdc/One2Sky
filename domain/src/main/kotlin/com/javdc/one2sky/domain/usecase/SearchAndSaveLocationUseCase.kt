package com.javdc.one2sky.domain.usecase

import com.javdc.one2sky.common.AsyncError
import com.javdc.one2sky.common.AsyncResult
import com.javdc.one2sky.common.NoCoordinatesError
import com.javdc.one2sky.common.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last

interface SearchAndSaveLocationUseCase {
    operator fun invoke(query: String): Flow<AsyncResult<Unit>>
}

class SearchAndSaveLocationUseCaseImpl(
    private val getWeatherForQueryUseCase: GetWeatherForQueryUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
) : SearchAndSaveLocationUseCase {

    override operator fun invoke(query: String): Flow<AsyncResult<Unit>> {
        return flow {
            emit(AsyncResult.Loading())
            when (val result = getWeatherForQueryUseCase(query).last()) {
                is AsyncResult.Success -> {
                    val location = result.data.location
                    if (location?.query != null) {
                        saveLocationUseCase(location)
                        emit(AsyncResult.Success(Unit))
                    } else {
                        emit(AsyncResult.Error(NoCoordinatesError))
                    }
                }
                is AsyncResult.Error -> {
                    emit(result.map { Unit })
                }
                else -> {
                    emit(AsyncResult.Error(AsyncError.UnknownError()))
                }
            }
        }
    }

}
