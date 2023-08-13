package com.fpoliveira.atraso.feat_route_details.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.navOptions
import com.fpoliveira.atraso.feat_route_details.domain.util.Resource
import com.fpoliveira.atraso.presentation.Screen
import com.fpoliveira.atraso.feat_route_details.presentation.text_train_details.components.Loading
import com.fpoliveira.atraso.feat_route_details.presentation.route_details_map.TrainScheduleDetailsMapScreen
import com.fpoliveira.atraso.viewModelEntryPoint
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.popUpTo
import java.time.ZonedDateTime

@Composable
fun TrainScheduleDetailsLoadingScreen(
    navController: NavController<Screen>,
    trainNumber: String,
    searchDate: ZonedDateTime
) {
    val viewModelEntryPoint = LocalContext.current.viewModelEntryPoint
    val viewModel: TrainScheduleDetailsViewModel = viewModel {
        viewModelEntryPoint.trainScheduleDetailsViewModelFactory().create(trainNumber, searchDate)
    }
    Crossfade(
        targetState = viewModel.scheduleState.value,
        animationSpec = tween(300, easing = EaseIn), label = "OnLoadRouteDetails"
    ) { targetState ->
        when(targetState) {
            is Resource.Success -> {
                navController.popUpTo { it is Screen.HomeScreen }
                navController.navigate(Screen.TrainScheduleDetailsMapScreen(targetState.data!!))
            }
            is Resource.Error -> Text(viewModel.scheduleState.value.message!!)
            is Resource.Loading -> Loading()
        }
    }
}
