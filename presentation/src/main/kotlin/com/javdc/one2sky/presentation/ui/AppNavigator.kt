package com.javdc.one2sky.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.javdc.one2sky.presentation.R
import com.javdc.one2sky.presentation.ui.locations.LocationsScreen
import com.javdc.one2sky.presentation.ui.theme.AppTheme
import com.javdc.one2sky.presentation.ui.weather.WeatherScreen
import kotlinx.serialization.Serializable

@Composable
fun MyNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Weather,
    ) {
        composable<Route.Weather> {
            WeatherScreen()
        }

        composable<Route.WeatherForQuery> { backStackEntry ->
            val weatherForQueryRoute: Route.WeatherForQuery = backStackEntry.toRoute()
            WeatherScreen(
                query = weatherForQueryRoute.query,
            )
        }

        composable<Route.Locations> {
            LocationsScreen(navController)
        }
    }
}

@Composable
fun AppNavigator() {
    val navController: NavHostController = rememberNavController()

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            MainAppScreen(navController = navController)
        }
    }
}

@Composable
fun MainAppScreen(navController: NavHostController) {
    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Horizontal),
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues(0.dp)
            ) {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_weather),
                                contentDescription = stringResource(R.string.weather_screen_title)
                            )
                        },
                        label = {
                            Text(stringResource(R.string.weather_screen_title))
                        },
                        selected = currentDestination?.hasRoute(Route.Weather::class) == true,
                        onClick = {
                            navController.navigate(Route.Weather) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )

                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.List,
                                contentDescription = stringResource(R.string.locations_screen_title)
                            )
                        },
                        label = {
                            Text(stringResource(R.string.locations_screen_title))
                        },
                        selected = currentDestination?.hasRoute(Route.Locations::class) == true,
                        onClick = {
                            navController.navigate(Route.Locations) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        Box(Modifier.padding(it)) {
            MyNavHost(navController)
        }
    }
}

@Serializable
sealed class Route {

    @Serializable
    data object Weather : Route()

    @Serializable
    data object Locations : Route()

    @Serializable
    data class WeatherForQuery(val query: String) : Route()

}
