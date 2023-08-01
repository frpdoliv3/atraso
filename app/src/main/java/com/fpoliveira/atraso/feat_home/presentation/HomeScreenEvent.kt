package com.fpoliveira.atraso.feat_home.presentation

import java.time.ZonedDateTime

sealed class HomeScreenEvent {
    data class OnTrainNumberChange(val trainNumber: String): HomeScreenEvent()
    data class OnTrainDateChange(val date: ZonedDateTime): HomeScreenEvent()
}
