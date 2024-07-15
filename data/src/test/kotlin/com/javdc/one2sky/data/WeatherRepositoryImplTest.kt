package com.javdc.one2sky.data

import com.javdc.one2sky.data.remote.datasource.WeatherRemoteDataSource
import com.javdc.one2sky.data.repository.WeatherRepositoryImpl
import com.javdc.one2sky.domain.repository.WeatherRepository
import com.javdc.one2sky.common.AsyncResult
import com.javdc.one2sky.domain.weatherBo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {

    private lateinit var weatherRepository: WeatherRepository

    @MockK
    private lateinit var weatherRemoteDataSource: WeatherRemoteDataSource

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        weatherRepository = WeatherRepositoryImpl(weatherRemoteDataSource)
    }

    @Test
    fun `when getWeather is called, then emit weather response from remote data source`() = runTest {
        // Given
        val expectedResponse = weatherBo
        coEvery { weatherRemoteDataSource.getWeather() } returns expectedResponse

        // When
        val result = weatherRepository.getWeather().last()
        val response = (result as? AsyncResult.Success)?.data

        // Then
        assert(result is AsyncResult.Success)
        assertEquals(expectedResponse, response)
    }

    @Test
    fun `when getWeather is called and remote data source throws exception, then emit an error`() = runTest {
        // Given
        coEvery { weatherRemoteDataSource.getWeather() } throws Exception()

        // When
        val result = weatherRepository.getWeather().last()

        // Then
        assert(result is AsyncResult.Error)
    }

    @Test
    fun `when getWeatherForQuery is called, then emit weather response from remote data source`() = runTest {
        // Given
        val expectedResponse = weatherBo
        coEvery { weatherRemoteDataSource.getWeatherForQuery(any()) } returns expectedResponse

        // When
        val result = weatherRepository.getWeatherForQuery("sevilla").last()
        val response = (result as? AsyncResult.Success)?.data

        // Then
        assert(result is AsyncResult.Success)
        assertEquals(expectedResponse, response)
    }

    @Test
    fun `when getWeatherForLocation is called and remote data source throws exception, then emit an error`() = runTest {
        // Given
        coEvery { weatherRemoteDataSource.getWeatherForQuery(any()) } throws Exception()

        // When
        val result = weatherRepository.getWeatherForQuery("sevilla").last()

        // Then
        assert(result is AsyncResult.Error)
    }

}
