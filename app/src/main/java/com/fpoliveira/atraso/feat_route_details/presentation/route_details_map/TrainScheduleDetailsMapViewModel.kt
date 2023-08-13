package com.fpoliveira.atraso.feat_route_details.presentation.route_details_map

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpoliveira.atraso.R
import com.fpoliveira.atraso.feat_route_details.domain.model.ScheduleInfo
import com.fpoliveira.atraso.feat_route_details.domain.model.StopData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TrainScheduleDetailsMapViewModel @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted scheduleInfo: ScheduleInfo
): ViewModel() {
    private val _selectedStopState: MutableState<StopData?> = mutableStateOf(null)
    val selectedStopState: State<StopData?> = _selectedStopState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _nextStopNotice = mutableStateOf("")
    val nextStopNotice: State<String> = _nextStopNotice

    init {
        val nextArrivalNode = scheduleInfo.trainPassageNodes.find { !it.trainPassed }
        if (nextArrivalNode == null) {
            _nextStopNotice.value = "dsfadsf"
        } else {
            val stationNameStr = context.getString(
                R.string.schedule_details_departure_in,
                nextArrivalNode.stationName
            )
            _nextStopNotice.value = context.resources.getQuantityString(
                R.plurals.schedule_details_time_plural,
                nextArrivalNode.scheduledTime.hour,
                stationNameStr,
                nextArrivalNode.scheduledTime.toString()
            )
        }
    }

    fun onEvent(event: TrainScheduleDetailsMapEvent) {
        when(event) {
            is TrainScheduleDetailsMapEvent.SelectedStopEvent -> {
                viewModelScope.launch { _eventFlow.emit(UiEvent.BottomSheetDataChange) }
                _selectedStopState.value = event.stopData
            }
            is TrainScheduleDetailsMapEvent.DismissEvent -> {
                viewModelScope.launch { _eventFlow.emit(UiEvent.HideBottomSheet) }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted context: Context,
            @Assisted scheduleInfo: ScheduleInfo
        ): TrainScheduleDetailsMapViewModel
    }

    sealed class UiEvent {
        object BottomSheetDataChange: UiEvent()
        object HideBottomSheet: UiEvent()
    }
}