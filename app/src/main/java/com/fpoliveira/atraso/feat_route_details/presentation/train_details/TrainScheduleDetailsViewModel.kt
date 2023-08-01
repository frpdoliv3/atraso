package com.fpoliveira.atraso.feat_route_details.presentation.train_details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpoliveira.atraso.feat_route_details.data.remote.SearchLocationDataDto
import com.fpoliveira.atraso.feat_route_details.data.remote.StationDetailsApi
import com.fpoliveira.atraso.feat_route_details.domain.model.ScheduleInfo
import com.fpoliveira.atraso.feat_route_details.domain.repository.ScheduleRepository
import com.fpoliveira.atraso.feat_route_details.domain.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class TrainScheduleDetailsViewModel @AssistedInject constructor(
    private val stationDetailsApi: StationDetailsApi,
    private val scheduleRepository: ScheduleRepository,
    @Assisted trainNumber: String,
    @Assisted searchDate: ZonedDateTime
): ViewModel() {
    private val _scheduleState: MutableState<Resource<ScheduleInfo>> = mutableStateOf(Resource.Loading())
    val scheduleState: State<Resource<ScheduleInfo>> = _scheduleState

    init {
        viewModelScope.launch {
            val teste = stationDetailsApi.getStationDetails(listOf(
                SearchLocationDataDto(
                    name = "Guimarães",
                    externalId = 9424000
                ),
                SearchLocationDataDto(
                    name = "VIZELA",
                    externalId = 9428233
                ),
                SearchLocationDataDto(
                    name = "SANTO TIRSO",
                    externalId = 9428068
                ),
                SearchLocationDataDto(
                    name = "COIMBRA-B",
                    externalId = 9436004
                ),
            ))
            Log.d("STATION_LOCATIONS", teste.body().toString())
            val schedule = scheduleRepository.getSchedule(trainNumber, searchDate)
            _scheduleState.value = schedule
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted trainNumber: String, @Assisted searchDate: ZonedDateTime): TrainScheduleDetailsViewModel
    }
}
