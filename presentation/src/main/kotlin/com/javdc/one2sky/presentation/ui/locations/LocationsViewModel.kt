package com.javdc.one2sky.presentation.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javdc.one2sky.common.AsyncResult
import com.javdc.one2sky.common.di.IoDispatcher
import com.javdc.one2sky.domain.model.LocationBo
import com.javdc.one2sky.domain.usecase.GetFavoriteQueryUseCase
import com.javdc.one2sky.domain.usecase.GetSavedLocationsUseCase
import com.javdc.one2sky.domain.usecase.RemoveSavedLocationUseCase
import com.javdc.one2sky.domain.usecase.SaveFavoriteQueryUseCase
import com.javdc.one2sky.domain.usecase.SearchAndSaveLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    @IoDispatcher private val iODispatcher: CoroutineDispatcher,
    getSavedLocationsUseCase: GetSavedLocationsUseCase,
    getFavoriteQueryUseCase: GetFavoriteQueryUseCase,
    private val searchAndSaveLocationUseCase: SearchAndSaveLocationUseCase,
    private val removeSavedLocationUseCase: RemoveSavedLocationUseCase,
    private val saveFavoriteQueryUseCase: SaveFavoriteQueryUseCase,
) : ViewModel() {

    private val _addLocationResultStateFlow = MutableStateFlow<AsyncResult<Unit>?>(null)
    val addLocationResultStateFlow: StateFlow<AsyncResult<Unit>?>
        get() = _addLocationResultStateFlow

    val locationsStateFlow: StateFlow<List<LocationBo>?> = getSavedLocationsUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            null,
        )

    val favoriteQueryStateFlow: StateFlow<String?> = getFavoriteQueryUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            null,
        )

    fun searchAndSaveLocation(query: String) {
        viewModelScope.launch(iODispatcher) {
            searchAndSaveLocationUseCase(query).collect { result ->
                _addLocationResultStateFlow.value = result
            }
        }
    }

    fun removeSavedLocation(location: LocationBo) {
        viewModelScope.launch(iODispatcher) {
            removeSavedLocationUseCase(location)
        }
    }

    fun saveFavoriteQuery(query: String?) {
        viewModelScope.launch(iODispatcher) {
            saveFavoriteQueryUseCase(query)
        }
    }

    fun dismissLocationResult() {
        _addLocationResultStateFlow.value = null
    }

}
