package com.fpoliveira.atraso.feat_route_details.data.station_details_remote

data class StationDto(
    val id: Long,
    val name: String,
    val location: LocationDto,
    val externalId: Long
)
