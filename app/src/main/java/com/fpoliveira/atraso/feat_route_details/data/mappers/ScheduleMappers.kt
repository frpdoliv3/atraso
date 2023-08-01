package com.fpoliveira.atraso.feat_route_details.data.mappers

import com.fpoliveira.atraso.feat_route_details.data.remote.ScheduleDataDto
import com.fpoliveira.atraso.feat_route_details.data.remote.StopInfoDto
import com.fpoliveira.atraso.feat_route_details.domain.model.ScheduleInfo
import com.fpoliveira.atraso.feat_route_details.domain.model.StopData
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun StopInfoDto.toStopData(): StopData {
    return StopData(
        stationName = stationName,
        trainPassed = trainPassed,
        scheduledTime = LocalTime.parse(scheduledTime, DateTimeFormatter.ISO_LOCAL_TIME),
        observations = observations
    )
}

fun ScheduleDataDto.toScheduleInfo(trainNumber: String): ScheduleInfo {
    val localDateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

    return ScheduleInfo(
        arrivalDate = ZonedDateTime.of(
            LocalDateTime.parse(arrivalDate, localDateTimeFormat),
            ZoneId.of(ScheduleInfo.DefaultTimezone)
        ),
        departureDate = ZonedDateTime.of(
            LocalDateTime.parse(departureDate, localDateTimeFormat),
            ZoneId.of(ScheduleInfo.DefaultTimezone)
        ),
        duration = LocalTime.parse(duration, DateTimeFormatter.ISO_LOCAL_TIME),
        departure = departure,
        destination = destination,
        operator = operator,
        serviceType = serviceType,
        trainStatus = trainStatus,
        trainNumber = trainNumber,
        trainPassageNodes = trainPassageNodes.map { it.toStopData() }
    )
}
