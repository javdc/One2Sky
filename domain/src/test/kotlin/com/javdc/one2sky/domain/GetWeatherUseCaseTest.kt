package com.javdc.one2sky.domain

import com.javdc.one2sky.common.AsyncResult
import com.javdc.one2sky.domain.repository.WeatherRepository
import com.javdc.one2sky.domain.usecase.GetFavoriteQueryUseCase
import com.javdc.one2sky.domain.usecase.GetWeatherUseCase
import com.javdc.one2sky.domain.usecase.GetWeatherUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetWeatherUseCaseTest {

    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    @MockK
    private lateinit var getFavoriteQueryUseCase: GetFavoriteQueryUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getWeatherUseCase = GetWeatherUseCaseImpl(weatherRepository, getFavoriteQueryUseCase)
    }

    @Test
    fun `when GetWeatherUseCase is invoked, then emit response from repository`() = runTest {
        // Given
        val expectedResult = AsyncResult.Success(weatherBo)
        every { weatherRepository.getWeather() } returns flowOf(expectedResult)
        every { getFavoriteQueryUseCase() } returns flowOf(null)

        // When
        val result = getWeatherUseCase(null).last()

        // Then
        assertEquals(expectedResult, result)
    }

}
