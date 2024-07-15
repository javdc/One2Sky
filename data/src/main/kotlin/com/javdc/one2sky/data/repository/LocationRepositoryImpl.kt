package com.javdc.one2sky.data.repository

import com.javdc.one2sky.data.local.datasource.LocationLocalDataSource
import com.javdc.one2sky.domain.model.LocationBo
import com.javdc.one2sky.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class LocationRepositoryImpl(
    private val locationLocalDataSource: LocationLocalDataSource,
) : LocationRepository {

    override fun getLocations(): Flow<List<LocationBo>> {
        return locationLocalDataSource.getLocations()
    }

    override suspend fun addLocation(location: LocationBo) {
        locationLocalDataSource.addLocation(location)
    }

    override suspend fun addLocations(locations: List<LocationBo>) {
        locationLocalDataSource.addLocations(locations)
    }

    override suspend fun removeLocation(location: LocationBo) {
        locationLocalDataSource.removeLocation(location)
    }

    override fun getFavoriteQuery(): Flow<String?> {
        return locationLocalDataSource.getFavoriteQuery()
    }

    override suspend fun saveFavoriteQuery(query: String?) {
        locationLocalDataSource.saveFavoriteQuery(query)
    }

}
