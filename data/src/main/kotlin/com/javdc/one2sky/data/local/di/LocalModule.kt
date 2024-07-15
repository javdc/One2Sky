package com.javdc.one2sky.data.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.javdc.one2sky.common.di.IoDispatcher
import com.javdc.one2sky.data.local.dao.LocationDao
import com.javdc.one2sky.data.local.database.AppDatabase
import com.javdc.one2sky.data.local.datasource.LocationLocalDataSource
import com.javdc.one2sky.data.local.datasource.LocationLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideRoom(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(context, AppDatabase::class.java, "one2sky-database")
            .build()

    @Provides
    @Singleton
    fun provideLocationDao(
        database: AppDatabase,
    ): LocationDao =
        database.locationDao()

    @Provides
    @Singleton
    fun provideDataStore(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        @ApplicationContext context: Context,
    ): DataStore<Preferences> {
        val datastore = PreferenceDataStoreFactory.create(
            scope = CoroutineScope(ioDispatcher)
        ) {
            context.preferencesDataStoreFile("${context.packageName}_preferences")
        }
        return datastore
    }

    @Provides
    @Singleton
    fun provideLocationLocalDataSource(
        locationDao: LocationDao,
        dataStore: DataStore<Preferences>,
    ): LocationLocalDataSource =
        LocationLocalDataSourceImpl(locationDao, dataStore)

}
