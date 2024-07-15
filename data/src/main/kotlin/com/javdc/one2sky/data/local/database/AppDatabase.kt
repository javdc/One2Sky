package com.javdc.one2sky.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.javdc.one2sky.data.local.dao.LocationDao
import com.javdc.one2sky.data.local.model.LocationDbo

@Database(entities = [LocationDbo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
