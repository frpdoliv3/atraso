package com.fpoliveira.atraso.feat_route_details.domain.repository

import com.fpoliveira.atraso.feat_route_details.domain.model.ScheduleInfo
import com.fpoliveira.atraso.feat_route_details.domain.util.Resource
import java.time.ZonedDateTime

interface ScheduleRepository {
    suspend fun getSchedule(trainNumber: String, searchDate: ZonedDateTime): Resource<ScheduleInfo>
}
