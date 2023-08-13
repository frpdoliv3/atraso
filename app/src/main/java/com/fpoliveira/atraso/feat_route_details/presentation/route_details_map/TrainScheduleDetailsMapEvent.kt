package com.fpoliveira.atraso.feat_route_details.presentation.route_details_map

import com.fpoliveira.atraso.feat_route_details.domain.model.StopData

sealed class TrainScheduleDetailsMapEvent {
    data class SelectedStopEvent(val stopData: StopData): TrainScheduleDetailsMapEvent()
    object DismissEvent: TrainScheduleDetailsMapEvent()
}
