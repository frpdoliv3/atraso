package com.fpoliveira.atraso.presentation.train_details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fpoliveira.atraso.domain.util.Resource
import com.fpoliveira.atraso.viewModelEntryPoint
import java.time.ZonedDateTime

@Composable
fun TrainScheduleDetailsScreen(
    trainNumber: Int,
    searchDate: ZonedDateTime
) {
    val viewModelEntryPoint = LocalContext.current.viewModelEntryPoint
    val viewModel: TrainScheduleDetailsViewModel = viewModel {
        viewModelEntryPoint.trainScheduleDetailsViewModelFactory().create(trainNumber, searchDate)
    }
    val data = when(viewModel.scheduleState.value) {
        is Resource.Success -> viewModel.scheduleState.value.data!!.toString()
        is Resource.Error -> viewModel.scheduleState.value.message!!
        is Resource.Loading -> "Loading"
    }
    Text(data)
}
