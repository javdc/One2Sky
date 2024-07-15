package com.javdc.one2sky.domain.usecase.di

import com.javdc.one2sky.domain.repository.LocationRepository
import com.javdc.one2sky.domain.repository.WeatherRepository
import com.javdc.one2sky.domain.usecase.GetFavoriteQueryUseCase
import com.javdc.one2sky.domain.usecase.GetFavoriteQueryUseCaseImpl
import com.javdc.one2sky.domain.usecase.SaveLocationUseCase
import com.javdc.one2sky.domain.usecase.SaveLocationUseCaseImpl
import com.javdc.one2sky.domain.usecase.GetSavedLocationsUseCase
import com.javdc.one2sky.domain.usecase.GetSavedLocationsUseCaseImpl
import com.javdc.one2sky.domain.usecase.GetWeatherForQueryUseCase
import com.javdc.one2sky.domain.usecase.GetWeatherForQueryUseCaseImpl
import com.javdc.one2sky.domain.usecase.GetWeatherUseCase
import com.javdc.one2sky.domain.usecase.GetWeatherUseCaseImpl
import com.javdc.one2sky.domain.usecase.RemoveSavedLocationUseCase
import com.javdc.one2sky.domain.usecase.RemoveSavedLocationUseCaseImpl
import com.javdc.one2sky.domain.usecase.SaveFavoriteQueryUseCase
import com.javdc.one2sky.domain.usecase.SaveFavoriteQueryUseCaseImpl
import com.javdc.one2sky.domain.usecase.SearchAndSaveLocationUseCase
import com.javdc.one2sky.domain.usecase.SearchAndSaveLocationUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetWeatherUseCase(
        weatherRepository: WeatherRepository,
        getFavoriteQueryUseCase: GetFavoriteQueryUseCase,
    ): GetWeatherUseCase =
        GetWeatherUseCaseImpl(weatherRepository, getFavoriteQueryUseCase)

    @Provides
    fun provideGetWeatherForQueryUseCase(
        weatherRepository: WeatherRepository,
    ): GetWeatherForQueryUseCase =
        GetWeatherForQueryUseCaseImpl(weatherRepository)

    @Provides
    fun provideGetSavedLocationsUseCase(
        locationRepository: LocationRepository,
    ): GetSavedLocationsUseCase =
        GetSavedLocationsUseCaseImpl(locationRepository)

    @Provides
    fun provideSaveLocationUseCase(
        locationRepository: LocationRepository,
    ): SaveLocationUseCase =
        SaveLocationUseCaseImpl(locationRepository)

    @Provides
    fun provideRemoveSavedLocationUseCase(
        locationRepository: LocationRepository,
    ): RemoveSavedLocationUseCase =
        RemoveSavedLocationUseCaseImpl(locationRepository)

    @Provides
    fun provideSearchAndSaveLocationUseCase(
        getWeatherForQueryUseCase: GetWeatherForQueryUseCase,
        saveLocationUseCase: SaveLocationUseCase,
    ): SearchAndSaveLocationUseCase =
        SearchAndSaveLocationUseCaseImpl(getWeatherForQueryUseCase, saveLocationUseCase)

    @Provides
    fun provideGetFavoriteQueryUseCase(
        locationRepository: LocationRepository,
    ): GetFavoriteQueryUseCase =
        GetFavoriteQueryUseCaseImpl(locationRepository)

    @Provides
    fun provideSaveFavoriteQueryUseCase(
        locationRepository: LocationRepository,
    ): SaveFavoriteQueryUseCase =
        SaveFavoriteQueryUseCaseImpl(locationRepository)

}
