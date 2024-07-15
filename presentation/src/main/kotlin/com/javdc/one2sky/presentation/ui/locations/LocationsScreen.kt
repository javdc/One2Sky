@file:OptIn(ExperimentalMaterial3Api::class)

package com.javdc.one2sky.presentation.ui.locations

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.javdc.one2sky.common.AsyncResult
import com.javdc.one2sky.domain.model.LocationBo
import com.javdc.one2sky.presentation.R
import com.javdc.one2sky.presentation.ui.Route
import com.javdc.one2sky.presentation.ui.common.LocationsProvider
import com.javdc.one2sky.presentation.ui.common.PreviewDarkLight
import com.javdc.one2sky.presentation.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun LocationsScreen(
    navController: NavController,
    viewModel: LocationsViewModel = hiltViewModel(),
) {
    val locations by viewModel.locationsStateFlow.collectAsStateWithLifecycle()
    val favoriteQuery by viewModel.favoriteQueryStateFlow.collectAsStateWithLifecycle()
    val addLocationResult by viewModel.addLocationResultStateFlow.collectAsStateWithLifecycle()

    LocationsScreen(
        locations = locations,
        favoriteQuery = favoriteQuery,
        onClickLocation = { location ->
            navController.navigate(Route.WeatherForQuery(location.query))
        },
        addLocationResult = addLocationResult,
        onDismissLocationResult = {
            viewModel.dismissLocationResult()
        },
        onClickAddLocation = {
            viewModel.searchAndSaveLocation(it)
        },
        onClickRemoveLocation = {
            viewModel.removeSavedLocation(it)
        },
        onClickSaveFavoriteQuery = {
            viewModel.saveFavoriteQuery(it)
        },
    )
}

@Composable
fun LocationsScreen(
    locations: List<LocationBo>?,
    favoriteQuery: String?,
    addLocationResult: AsyncResult<Unit>?,
    onDismissLocationResult: () -> Unit,
    onClickLocation: (LocationBo) -> Unit,
    onClickAddLocation: (String) -> Unit,
    onClickRemoveLocation: (LocationBo) -> Unit,
    onClickSaveFavoriteQuery: (String?) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val dialogOpen = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    addLocationResult?.let {
        if (addLocationResult is AsyncResult.Success || addLocationResult is AsyncResult.Error) {
            val snackbarMessage = if (addLocationResult is AsyncResult.Success) {
                stringResource(R.string.locations_add_success)
            } else {
                stringResource(R.string.locations_add_error)
            }
            scope.launch {
                val snackbarResult = snackbarHostState.showSnackbar(snackbarMessage)
                when (snackbarResult) {
                    SnackbarResult.Dismissed -> onDismissLocationResult()
                    else -> { /* no-op */ }
                }
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.locations_screen_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            LocationsList(
                locations = locations,
                favoriteQuery = favoriteQuery,
                onClickLocation = onClickLocation,
                onClickRemoveLocation = onClickRemoveLocation,
                onClickSaveFavoriteQuery = onClickSaveFavoriteQuery,
            )
            if (addLocationResult is AsyncResult.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            ExtendedFloatingActionButton(
                onClick = { dialogOpen.value = true },
                icon = { Icon(Icons.Filled.Add, null) },
                text = { Text(stringResource(R.string.locations_action_add_location)) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            )
            AddLocationAlertDialog(
                dialogOpen = dialogOpen,
                onClickAddLocation = onClickAddLocation,
            )
        }
    }
}

@Composable
fun LocationsList(
    locations: List<LocationBo>?,
    favoriteQuery: String?,
    onClickLocation: (LocationBo) -> Unit,
    onClickRemoveLocation: (LocationBo) -> Unit,
    onClickSaveFavoriteQuery: (String?) -> Unit,
) {
    locations?.let {
        Crossfade(
            targetState = locations.isNotEmpty(),
            label = "Locations list crossfade",
        ) { locationsNotEmpty ->
            if (locationsNotEmpty) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = PaddingValues(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 104.dp),
                ) {
                    items(locations, key = { it.query }) { location ->
                        LocationItem(
                            location = location,
                            favoriteQuery = favoriteQuery,
                            onClick = onClickLocation,
                            onClickRemove = onClickRemoveLocation,
                            onClickSaveFavoriteQuery = onClickSaveFavoriteQuery,
                            modifier = Modifier.animateItem(),
                        )
                    }
                }
            } else {
                NoLocationsMessage()
            }
        }
    }
}

@Composable
private fun NoLocationsMessage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        Text(
            text = stringResource(R.string.locations_empty_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = stringResource(R.string.locations_empty_body),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun LocationItem(
    location: LocationBo,
    favoriteQuery: String?,
    onClick: (LocationBo) -> Unit,
    onClickRemove: (LocationBo) -> Unit,
    onClickSaveFavoriteQuery: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(location) },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 2.dp,
                    alignment = Alignment.CenterVertically,
                ),
            ) {
                Text(
                    text = location.areaName ?: stringResource(R.string.unknown),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = location.region ?: stringResource(R.string.unknown),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(
                modifier = Modifier.weight(1f),
            )
            if (location.query == favoriteQuery) {
                IconButton(
                    onClick = { onClickSaveFavoriteQuery(null) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = stringResource(R.string.action_remove_from_favorites)
                    )
                }
            } else {
                IconButton(
                    onClick = { onClickSaveFavoriteQuery(location.query) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = stringResource(R.string.action_add_to_favorites)
                    )
                }
            }
            IconButton(
                onClick = { onClickRemove(location) },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = stringResource(R.string.action_delete)
                )
            }
        }
    }
}

@Composable
fun AddLocationAlertDialog(
    dialogOpen: MutableState<Boolean>,
    onClickAddLocation: (String) -> Unit,
) {
    var query by remember { mutableStateOf("") }

    if (dialogOpen.value) {
        AlertDialog(
            onDismissRequest = {
                dialogOpen.value = false
            },
            title = {
                Text(stringResource(R.string.add_location_dialog_title))
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(stringResource(R.string.add_location_dialog_body))
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        label = { Text(stringResource(R.string.add_location_dialog_input_hint)) },
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClickAddLocation(query)
                        query = ""
                        dialogOpen.value = false
                    }
                ) {
                    Text(stringResource(R.string.action_add))
                }
            }
        )
    }
}

@Composable
@PreviewDarkLight
fun LocationsScreenPreview(@PreviewParameter(LocationsProvider::class) locations: List<LocationBo>) {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            LocationsScreen(
                locations = locations,
                favoriteQuery = locations.firstOrNull()?.query,
                addLocationResult = AsyncResult.Success(Unit),
                onDismissLocationResult = { /* no-op */ },
                onClickLocation = { /* no-op */ },
                onClickAddLocation = { /* no-op */ },
                onClickRemoveLocation = { /* no-op */ },
                onClickSaveFavoriteQuery = { /* no-op */ },
            )
        }
    }
}

@Composable
@PreviewDarkLight
fun AddLocationAlertDialogPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            AddLocationAlertDialog(
                dialogOpen = remember { mutableStateOf(true) },
                onClickAddLocation = { /* no-op */ }
            )
        }
    }
}
