package com.fpoliveira.atraso.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

sealed class Screen: Parcelable {
    @Parcelize
    object HomeScreen: Screen()

    @Parcelize
    data class TrainScheduleDetailsScreen(val trainNumber: Int, val searchDate: ZonedDateTime): Screen()
}