package com.fpoliveira.atraso.presentation.train_details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInBack
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInQuad
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fpoliveira.atraso.domain.util.Resource
import com.fpoliveira.atraso.presentation.Screen
import com.fpoliveira.atraso.presentation.train_details.components.Loading
import com.fpoliveira.atraso.presentation.train_details.components.TrainScheduleDetails
import com.fpoliveira.atraso.viewModelEntryPoint
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.navController
import java.time.ZonedDateTime

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TrainScheduleDetailsScreen(
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
        animationSpec = tween(300, easing = EaseIn)
    ) { targetState ->
        when(targetState) {
            is Resource.Success -> TrainScheduleDetails(
                navController = navController,
                scheduleInfo = viewModel.scheduleState.value.data!!
            )
            is Resource.Error -> Text(viewModel.scheduleState.value.message!!)
            is Resource.Loading -> Loading()
        }
    }
}
