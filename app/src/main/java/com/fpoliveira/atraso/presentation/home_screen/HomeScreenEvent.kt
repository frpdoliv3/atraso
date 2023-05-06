package com.fpoliveira.atraso.presentation.home_screen

import java.time.ZonedDateTime

sealed class HomeScreenEvent {
    data class OnTrainNumberChange(val trainNumber: String): HomeScreenEvent()
    data class OnTrainDateChange(val date: ZonedDateTime): HomeScreenEvent()
}
