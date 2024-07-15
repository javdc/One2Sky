package com.javdc.one2sky.domain.usecase

import com.javdc.one2sky.domain.model.LocationBo
import com.javdc.one2sky.domain.repository.LocationRepository

interface RemoveSavedLocationUseCase {
    suspend operator fun invoke(location: LocationBo)
}

class RemoveSavedLocationUseCaseImpl(
    private val locationRepository: LocationRepository,
) : RemoveSavedLocationUseCase {

    override suspend operator fun invoke(location: LocationBo) {
        return locationRepository.removeLocation(location)
    }

}
