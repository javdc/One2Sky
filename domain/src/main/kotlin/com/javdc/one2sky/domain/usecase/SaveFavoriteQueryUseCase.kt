package com.javdc.one2sky.domain.usecase

import com.javdc.one2sky.domain.repository.LocationRepository

interface SaveFavoriteQueryUseCase {
    suspend operator fun invoke(query: String?)
}

class SaveFavoriteQueryUseCaseImpl(
    private val locationRepository: LocationRepository,
) : SaveFavoriteQueryUseCase {

    override suspend operator fun invoke(query: String?) {
        return locationRepository.saveFavoriteQuery(query)
    }

}
