package com.fpoliveira.atraso.presentation

import android.os.Parcelable
import com.fpoliveira.atraso.feat_route_details.domain.model.ScheduleInfo
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.time.ZonedDateTime

sealed class Screen: Parcelable {
    @Parcelize
    object HomeScreen: Screen()

    @Parcelize
    data class TrainScheduleDetailsScreen(val trainNumber: String, val searchDate: ZonedDateTime): Screen()

    @Parcelize
    data class TrainScheduleDetailsMapScreen(val scheduleInfo: @RawValue ScheduleInfo): Screen()
}