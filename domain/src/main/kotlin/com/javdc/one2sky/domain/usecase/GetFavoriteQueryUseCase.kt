package com.javdc.one2sky.domain.usecase

import com.javdc.one2sky.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

interface GetFavoriteQueryUseCase {
    operator fun invoke(): Flow<String?>
}

class GetFavoriteQueryUseCaseImpl(
    private val locationRepository: LocationRepository,
) : GetFavoriteQueryUseCase {

    override operator fun invoke(): Flow<String?> {
        return locationRepository.getFavoriteQuery()
    }

}
