package com.fpoliveira.atraso.feat_route_details.presentation.train_details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpoliveira.atraso.feat_route_details.data.station_details_remote.SearchLocationDataDto
import com.fpoliveira.atraso.feat_route_details.data.schedule_remote.StationDetailsApi
import com.fpoliveira.atraso.feat_route_details.domain.model.ScheduleInfo
import com.fpoliveira.atraso.feat_route_details.domain.repository.ScheduleRepository
import com.fpoliveira.atraso.feat_route_details.domain.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class TrainScheduleDetailsViewModel @AssistedInject constructor(,
    private val scheduleRepository: ScheduleRepository,
    @Assisted trainNumber: String,
    @Assisted searchDate: ZonedDateTime
): ViewModel() {
    private val _scheduleState: MutableState<Resource<ScheduleInfo>> = mutableStateOf(Resource.Loading())
    val scheduleState: State<Resource<ScheduleInfo>> = _scheduleState

    init {
        viewModelScope.launch {
            val schedule = scheduleRepository.getSchedule(trainNumber, searchDate)
            _scheduleState.value = schedule
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted trainNumber: String, @Assisted searchDate: ZonedDateTime): TrainScheduleDetailsViewModel
    }
}
