package com.javdc.one2sky.presentation.ui.weather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.javdc.one2sky.common.AsyncResult
import com.javdc.one2sky.common.di.IoDispatcher
import com.javdc.one2sky.common.util.tryOrNull
import com.javdc.one2sky.domain.model.WeatherBo
import com.javdc.one2sky.domain.usecase.GetWeatherUseCase
import com.javdc.one2sky.presentation.ui.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @IoDispatcher private val iODispatcher: CoroutineDispatcher,
    private val getWeatherUseCase: GetWeatherUseCase,
) : ViewModel() {

    private val _weatherState = MutableStateFlow<AsyncResult<WeatherBo>>(AsyncResult.Loading())
    val weatherState: StateFlow<AsyncResult<WeatherBo>>
        get() = _weatherState

    init {
        val query = tryOrNull(logException = false) {
            savedStateHandle.toRoute<Route.WeatherForQuery>().query
        }
        fetchWeather(query)
    }

    fun fetchWeather(query: String?) {
        viewModelScope.launch(iODispatcher) {
            getWeatherUseCase(query).collect {
                _weatherState.value = it
            }
        }
    }

}
