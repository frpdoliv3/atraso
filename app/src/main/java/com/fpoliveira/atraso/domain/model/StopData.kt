package com.fpoliveira.atraso.domain.model

import java.time.LocalTime

data class StopData(
    val stationName: String,
    val trainPassed: Boolean,
    val scheduledTime: LocalTime,
    val observations: String
)
