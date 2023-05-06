package com.fpoliveira.atraso.domain.model

import java.time.LocalTime
import java.time.ZonedDateTime

data class ScheduleInfo(
    val arrivalDate: ZonedDateTime,
    val departureDate: ZonedDateTime,
    val duration: LocalTime,
    val departure: String,
    val destination: String,
    val operator: String,
    val serviceType: String,
    val trainStatus: String,
    val trainPassageNodes: List<StopData>
) {
    companion object {
        const val DefaultTimezone = "Europe/Lisbon"
    }
}
