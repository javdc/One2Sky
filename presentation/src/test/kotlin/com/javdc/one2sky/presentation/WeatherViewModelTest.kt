@file:OptIn(ExperimentalCoroutinesApi::class)
@file:Suppress("IllegalIdentifier")

package com.javdc.one2sky.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.javdc.one2sky.common.AsyncResult
import com.javdc.one2sky.domain.model.WeatherBo
import com.javdc.one2sky.domain.usecase.GetWeatherUseCase
import com.javdc.one2sky.domain.weatherBo
import com.javdc.one2sky.presentation.ui.weather.WeatherViewModel
import com.javdc.one2sky.presentation.util.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherViewModelTest {

    @MockK
    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when WeatherViewModel is initialized, then weather is fetched one time and results are emitted in state`() = runTest {
        // Given
        val expected = weatherBo
        every { getWeatherUseCase(any()) } returns flow {
            emit(AsyncResult.Loading())
            delay(1000)
            emit(AsyncResult.Success(expected))
        }
        val weatherViewModel = WeatherViewModel(
            SavedStateHandle(),
            coroutineTestRule.testDispatcher,
            getWeatherUseCase,
        )

        // When
        // Call is made at initialization

        // Then
        weatherViewModel.weatherState.test {
            assertEquals(AsyncResult.Loading<WeatherBo>(), awaitItem())
            assertEquals(AsyncResult.Success(expected), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        coVerify(exactly = 1) { getWeatherUseCase(any()) }
    }

}
