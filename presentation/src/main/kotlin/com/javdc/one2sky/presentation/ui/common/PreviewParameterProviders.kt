package com.javdc.one2sky.presentation.ui.common

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.javdc.one2sky.domain.model.ForecastBo
import com.javdc.one2sky.domain.model.LocationBo
import com.javdc.one2sky.domain.model.WeatherBo
import com.javdc.one2sky.common.AsyncError
import com.javdc.one2sky.common.AsyncResult
import com.javdc.one2sky.presentation.ui.common.PreviewParameterData.forecast
import com.javdc.one2sky.presentation.ui.common.PreviewParameterData.locations
import com.javdc.one2sky.presentation.ui.common.PreviewParameterData.weather
import java.time.LocalDateTime

class AsyncErrorProvider : PreviewParameterProvider<AsyncError> {
    override val values = sequenceOf(
        AsyncError.DataParseError,
        AsyncError.ConnectionError,
        AsyncError.TimeoutError,
        AsyncError.DataParseError,
        AsyncError.ServerError(404),
        AsyncError.UnknownError(IllegalArgumentException()),
        AsyncError.EmptyResponseError,
        AsyncError.CustomError(),
    )
}

class LambdaProvider : PreviewParameterProvider<() -> Unit> {
    override val values = sequenceOf(
        { /* no-op */ }
    )
}

class WeatherStateProvider : PreviewParameterProvider<AsyncResult<WeatherBo>> {
    override val values = sequenceOf(
        AsyncResult.Success(weather),
        AsyncResult.Error(AsyncError.ConnectionError),
    )
}

class WeatherProvider : PreviewParameterProvider<WeatherBo> {
    override val values = sequenceOf(weather)
}

class ForecastProvider : PreviewParameterProvider<ForecastBo> {
    override val values = forecast.asSequence()
}

class LocationsProvider : PreviewParameterProvider<List<LocationBo>> {
    override val values = sequenceOf(
        locations,
        listOf(),
    )
}

object PreviewParameterData {

    private val now = LocalDateTime.now()

    val locations = listOf(
        LocationBo(
            areaName = "Sevilla",
            region = "Andalucía",
            query = "sevilla",
        ),
        LocationBo(
            areaName = "Cádiz",
            region = "Andalucía",
            query = "cadiz andalucia",
        ),
        LocationBo(
            areaName = "Málaga",
            region = "Andalucía",
            query = "malaga",
        ),
        LocationBo(
            areaName = "Madrid",
            region = "Madrid",
            query = "madrid",
        ),
    )

    val forecast = listOf(
        ForecastBo(
            now.toLocalDate(),
            28,
            44,
        ),
        ForecastBo(
            now.toLocalDate().plusDays(1),
            27,
            41,
        ),
        ForecastBo(
            now.toLocalDate().plusDays(2),
            25,
            39,
        ),
    )

    val weather = WeatherBo(
        location = locations.first(),
        observationDateTime = now,
        description = "Partly cloudy",
        descriptionEs = "Parcialmente nublado",
        tempCelsius = 42,
        feelsLikeTempCelsius = 44,
        humidity = 69,
        windSpeedKmh = 23,
        forecast = forecast,
    )

}
