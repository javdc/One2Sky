package com.javdc.one2sky.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.javdc.one2sky.data.local.model.LocationDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM location")
    fun getLocations(): Flow<List<LocationDbo>>

    @Upsert
    suspend fun addLocation(location: LocationDbo)

    @Upsert
    suspend fun addLocations(locations: List<LocationDbo>)

    @Query("DELETE FROM location where `query` = :query")
    suspend fun removeLocation(query: String)

}
