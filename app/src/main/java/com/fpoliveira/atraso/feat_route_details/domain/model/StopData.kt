package com.fpoliveira.atraso.feat_route_details.domain.model

import java.time.LocalTime

data class StopData(
    val id: Long? = null,
    val location: Location? = null,
    val stationName: String,
    val trainPassed: Boolean,
    val scheduledTime: LocalTime,
    val observations: String,
    val externalId: Long,
)
