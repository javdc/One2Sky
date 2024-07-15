package com.javdc.one2sky.domain.usecase

import com.javdc.one2sky.domain.model.LocationBo
import com.javdc.one2sky.domain.repository.LocationRepository

interface SaveLocationUseCase {
    suspend operator fun invoke(location: LocationBo)
}

class SaveLocationUseCaseImpl(
    private val locationRepository: LocationRepository,
) : SaveLocationUseCase {

    override suspend operator fun invoke(location: LocationBo) {
        return locationRepository.addLocation(location)
    }

}
