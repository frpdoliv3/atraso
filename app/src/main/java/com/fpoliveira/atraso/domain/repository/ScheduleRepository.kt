package com.fpoliveira.atraso.domain.repository

import com.fpoliveira.atraso.domain.model.ScheduleInfo
import com.fpoliveira.atraso.domain.util.Resource
import java.time.ZonedDateTime

interface ScheduleRepository {
    suspend fun getSchedule(trainNumber: String, searchDate: ZonedDateTime): Resource<ScheduleInfo>
}
