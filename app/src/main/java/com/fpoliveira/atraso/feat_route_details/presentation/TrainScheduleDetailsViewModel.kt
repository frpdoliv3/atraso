package com.fpoliveira.atraso.feat_route_details.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpoliveira.atraso.feat_route_details.domain.model.ScheduleInfo
import com.fpoliveira.atraso.feat_route_details.domain.util.Resource
import com.fpoliveira.atraso.feat_route_details.use_case.GetRouteDetailsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class TrainScheduleDetailsViewModel @AssistedInject constructor(
    private val getDetailsUseCase: GetRouteDetailsUseCase,
    @Assisted trainNumber: String,
    @Assisted searchDate: ZonedDateTime
): ViewModel() {
    private val _scheduleState: MutableState<Resource<ScheduleInfo>> = mutableStateOf(Resource.Loading())
    val scheduleState: State<Resource<ScheduleInfo>> = _scheduleState

    init {
        viewModelScope.launch {
            _scheduleState.value = getDetailsUseCase(trainNumber, searchDate)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted trainNumber: String, @Assisted searchDate: ZonedDateTime): TrainScheduleDetailsViewModel
    }
}
