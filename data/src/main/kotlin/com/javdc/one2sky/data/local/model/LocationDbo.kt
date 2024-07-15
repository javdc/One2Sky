package com.javdc.one2sky.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationDbo(
    @PrimaryKey @ColumnInfo(name = "query") val query: String,
    @ColumnInfo(name = "areaName") val areaName: String?,
    @ColumnInfo(name = "region") val region: String?,
)
