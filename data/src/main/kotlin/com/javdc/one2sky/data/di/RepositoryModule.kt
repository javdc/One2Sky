package com.javdc.one2sky.data.di

import com.javdc.one2sky.data.local.datasource.LocationLocalDataSource
import com.javdc.one2sky.data.remote.datasource.WeatherRemoteDataSource
import com.javdc.one2sky.data.repository.LocationRepositoryImpl
import com.javdc.one2sky.data.repository.WeatherRepositoryImpl
import com.javdc.one2sky.domain.repository.LocationRepository
import com.javdc.one2sky.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherRemoteDataSource: WeatherRemoteDataSource,
    ): WeatherRepository {
        return WeatherRepositoryImpl(
            weatherRemoteDataSource,
        )
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        weatherLocalDataSource: LocationLocalDataSource,
    ): LocationRepository {
        return LocationRepositoryImpl(
            weatherLocalDataSource,
        )
    }

}
