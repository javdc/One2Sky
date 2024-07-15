package com.javdc.one2sky.data.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.javdc.one2sky.common.util.takeIfNotBlank
import com.javdc.one2sky.data.local.dao.LocationDao
import com.javdc.one2sky.data.local.mapper.toLocationBo
import com.javdc.one2sky.data.local.mapper.toLocationDbo
import com.javdc.one2sky.domain.model.LocationBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface LocationLocalDataSource {
    fun getLocations(): Flow<List<LocationBo>>
    suspend fun addLocation(location: LocationBo)
    suspend fun addLocations(locations: List<LocationBo>)
    suspend fun removeLocation(location: LocationBo)
    fun getFavoriteQuery(): Flow<String?>
    suspend fun saveFavoriteQuery(query: String?)
}

class LocationLocalDataSourceImpl(
    private val locationDao: LocationDao,
    private val dataStore: DataStore<Preferences>
) : LocationLocalDataSource {

    override fun getLocations(): Flow<List<LocationBo>> =
        locationDao.getLocations().map { list -> list.map { it.toLocationBo() } }

    override suspend fun addLocation(location: LocationBo) {
        locationDao.addLocation(location.toLocationDbo())
    }

    override suspend fun addLocations(locations: List<LocationBo>) {
        locationDao.addLocations(locations.map { it.toLocationDbo() })
    }

    override suspend fun removeLocation(location: LocationBo) {
        locationDao.removeLocation(location.query)
    }

    override fun getFavoriteQuery(): Flow<String?> =
        dataStore.data.map { it[PREF_FAVORITE_QUERY]?.takeIfNotBlank() }

    override suspend fun saveFavoriteQuery(query: String?) {
        dataStore.edit { settings ->
            settings[PREF_FAVORITE_QUERY] = query.orEmpty()
        }
    }

    private companion object {
        private val PREF_FAVORITE_QUERY = stringPreferencesKey("favorite_query")
    }

}
