@file:OptIn(ExperimentalMaterial3Api::class)

package com.javdc.one2sky.presentation.ui.weather

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.javdc.one2sky.domain.model.ForecastBo
import com.javdc.one2sky.domain.model.WeatherBo
import com.javdc.one2sky.common.AsyncResult
import com.javdc.one2sky.presentation.R
import com.javdc.one2sky.presentation.ui.common.AsyncStateHandler
import com.javdc.one2sky.presentation.ui.common.PreviewDarkLight
import com.javdc.one2sky.presentation.ui.common.TextWithIcon
import com.javdc.one2sky.presentation.ui.common.WeatherProvider
import com.javdc.one2sky.presentation.ui.common.WeatherStateProvider
import com.javdc.one2sky.presentation.ui.theme.AppTheme
import com.javdc.one2sky.presentation.ui.theme.maximumTemperatureDark
import com.javdc.one2sky.presentation.ui.theme.maximumTemperatureLight
import com.javdc.one2sky.presentation.ui.theme.minimumTemperatureDark
import com.javdc.one2sky.presentation.ui.theme.minimumTemperatureLight
import com.javdc.one2sky.presentation.util.formatRelativeShortDate
import java.time.format.DateTimeFormatter

@Composable
fun WeatherScreen(
    query: String? = null,
    viewModel: WeatherViewModel = hiltViewModel(),
) {
    val weatherState by viewModel.weatherState.collectAsStateWithLifecycle()

    WeatherScreen(
        weatherState = weatherState,
        onRetry = { viewModel.fetchWeather(query) },
    )
}

@Composable
fun WeatherScreen(
    weatherState: AsyncResult<WeatherBo>,
    onRetry: () -> Unit,
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Crossfade(
                        targetState = (weatherState as? AsyncResult.Success)?.data?.location?.areaName,
                        label = "Weather title crossfade"
                    ) { targetAreaName ->
                        Text(
                            text = targetAreaName?.let {
                                stringResource(R.string.weather_screen_title_format, it)
                            } ?: run {
                                stringResource(R.string.weather_screen_title)
                            },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            AsyncStateHandler(
                asyncResult = weatherState,
                onClickRetry = { onRetry() },
            ) { data ->
                Weather(
                    weather = data,
                )
            }
        }
    }
}

@Composable
fun Weather(@PreviewParameter(WeatherProvider::class) weather: WeatherBo) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(24.dp),
    ) {
        item {
            Text(
                text = stringResource(R.string.weather_temp_celsius_format, weather.tempCelsius ?: "--"),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Light,
                    fontSize = 72.sp,
                ),
                modifier = Modifier.fillMaxWidth()
            )

            val description = if (Locale.current.language == Locale("es").language) {
                    weather.descriptionEs
                } else {
                    weather.description
            }
            description?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 8.dp),
            ) {
                weather.feelsLikeTempCelsius?.let {
                    CurrentWeatherInfoItem(
                        iconDrawableResId = R.drawable.ic_temperature,
                        text = stringResource(R.string.weather_temp_celsius_format, it),
                        description = stringResource(R.string.weather_feels_like),
                    )
                }
                weather.windSpeedKmh?.let {
                    CurrentWeatherInfoItem(
                        iconDrawableResId = R.drawable.ic_wind,
                        text = stringResource(R.string.weather_wind_kmh_format, it),
                        description = stringResource(R.string.weather_wind),
                    )
                }
                weather.humidity?.let {
                    CurrentWeatherInfoItem(
                        iconDrawableResId = R.drawable.ic_humidity,
                        text = stringResource(R.string.weather_humidity_percent_format, it),
                        description = stringResource(R.string.weather_humidity),
                    )
                }
            }
        }

        weather.forecast?.let {
            items(it) { forecast ->
                ForecastItem(
                    forecast = forecast,
                )
            }
        }

        weather.observationDateTime?.let {
            item {
                TextWithIcon(
                    text = stringResource(
                        R.string.weather_last_update_format,
                        it.format(DateTimeFormatter.ofPattern(stringResource(R.string.date_time_format_default)))
                    ),
                    fontSize = 14.sp,
                    iconPainter = painterResource(R.drawable.ic_clock),
                    iconSize = 14.dp,
                    iconPadding = 4.dp,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CurrentWeatherInfoItem(@DrawableRes iconDrawableResId: Int, text: String, description: String?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = iconDrawableResId),
            contentDescription = null,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
            ),
        )
        description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
fun ForecastItem(forecast: ForecastBo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(12.dp),
        ) {
            Text(
                text = forecast.date.formatRelativeShortDate(LocalContext.current),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                ),
            )
            forecast.maxTempCelsius?.let {
                TextWithIcon(
                    text = stringResource(R.string.weather_max_temp_celsius_format, it),
                    style = MaterialTheme.typography.labelLarge,
                    iconPainter = painterResource(R.drawable.ic_temperature_high),
                    iconSize = 18.dp,
                    iconTint = if (isSystemInDarkTheme()) maximumTemperatureDark else maximumTemperatureLight,
                )
            }
            forecast.minTempCelsius?.let {
                TextWithIcon(
                    text = stringResource(R.string.weather_min_temp_celsius_format, it),
                    style = MaterialTheme.typography.labelLarge,
                    iconPainter = painterResource(R.drawable.ic_temperature_low),
                    iconSize = 18.dp,
                    iconTint = if (isSystemInDarkTheme()) minimumTemperatureDark else minimumTemperatureLight,
                )
            }
        }
    }
}

@Composable
@PreviewDarkLight
fun WeatherScreenPreview(@PreviewParameter(WeatherStateProvider::class) weatherState: AsyncResult<WeatherBo>) {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            WeatherScreen(
                weatherState = weatherState,
                onRetry = { /* no-op */ },
            )
        }
    }
}
