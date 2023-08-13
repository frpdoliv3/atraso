package com.fpoliveira.atraso.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.fpoliveira.atraso.feat_home.presentation.HomeScreen
import com.fpoliveira.atraso.feat_route_details.presentation.TrainScheduleDetailsLoadingScreen
import com.fpoliveira.atraso.feat_route_details.presentation.route_details_map.TrainScheduleDetailsMapScreen
import com.fpoliveira.atraso.presentation.ui.theme.AtrasoTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.olshevski.navigation.reimagined.AnimatedNavHost
import dev.olshevski.navigation.reimagined.NavAction
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.rememberNavController

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController<Screen>(
                startDestination = Screen.HomeScreen
            )
            NavBackHandler(navController)
            AtrasoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimatedNavHost(
                        controller = navController,
                        transitionSpec = { action, _, _ ->
                            val direction = if (action == NavAction.Pop) {
                                AnimatedContentScope.SlideDirection.End
                            } else {
                                AnimatedContentScope.SlideDirection.Start
                            }
                            slideIntoContainer(direction) with slideOutOfContainer(direction)
                        }
                    ) { screen ->
                        when(screen) {
                            is Screen.HomeScreen -> HomeScreen(navController)
                            is Screen.TrainScheduleDetailsScreen -> TrainScheduleDetailsLoadingScreen(
                                navController,
                                screen.trainNumber,
                                screen.searchDate
                            )
                            is Screen.TrainScheduleDetailsMapScreen -> TrainScheduleDetailsMapScreen(
                                navController,
                                screen.scheduleInfo
                            )
                        }
                    }
                }
            }
        }
    }
}
