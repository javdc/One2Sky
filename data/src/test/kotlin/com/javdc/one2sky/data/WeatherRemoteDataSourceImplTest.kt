package com.javdc.one2sky.data

import com.javdc.one2sky.data.remote.api.WeatherService
import com.javdc.one2sky.data.remote.datasource.WeatherRemoteDataSource
import com.javdc.one2sky.data.remote.datasource.WeatherRemoteDataSourceImpl
import com.javdc.one2sky.data.remote.mapper.toWeatherBo
import com.javdc.one2sky.data.remote.wttrResponseDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class WeatherRemoteDataSourceImplTest {

    private lateinit var weatherRemoteDataSource: WeatherRemoteDataSource

    @MockK
    private lateinit var weatherService: WeatherService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        weatherRemoteDataSource = WeatherRemoteDataSourceImpl(weatherService)
    }

    @Test
    fun `when getWeather is called, then return the result from the service mapped from dto to bo`() = runTest {
        // Given
        coEvery { weatherService.getWeather() } returns wttrResponseDto

        // When
        val weather = weatherRemoteDataSource.getWeather()

        // Then
        assert(weather == wttrResponseDto.toWeatherBo())
    }

    @Test
    fun `when getWeather is called and service throws an exception, then throw the same exception`() = runTest {
        // Given
        coEvery { weatherService.getWeather() } throws HttpException(
            Response.error<Unit>(HttpURLConnection.HTTP_BAD_REQUEST, "".toResponseBody(null)),
        )

        // When
        try {
            weatherRemoteDataSource.getWeather()
        } catch (e: Exception) {
            // Then
            assert(e is HttpException)
        }
    }

}
