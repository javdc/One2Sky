package com.javdc.one2sky.domain.usecase

import com.javdc.one2sky.domain.model.LocationBo
import com.javdc.one2sky.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

interface GetSavedLocationsUseCase {
    operator fun invoke(): Flow<List<LocationBo>>
}

class GetSavedLocationsUseCaseImpl(
    private val locationRepository: LocationRepository,
) : GetSavedLocationsUseCase {

    override operator fun invoke(): Flow<List<LocationBo>> {
        return locationRepository.getLocations()
    }

}
