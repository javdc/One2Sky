package com.javdc.one2sky.domain.repository

import com.javdc.one2sky.domain.model.LocationBo
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocations(): Flow<List<LocationBo>>
    suspend fun addLocation(location: LocationBo)
    suspend fun addLocations(locations: List<LocationBo>)
    suspend fun removeLocation(location: LocationBo)
    fun getFavoriteQuery(): Flow<String?>
    suspend fun saveFavoriteQuery(query: String?)
}
