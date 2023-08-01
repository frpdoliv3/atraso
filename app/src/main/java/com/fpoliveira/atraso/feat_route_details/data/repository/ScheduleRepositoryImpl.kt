package com.fpoliveira.atraso.feat_route_details.data.repository

import android.content.Context
import com.fpoliveira.atraso.feat_route_details.data.mappers.toScheduleInfo
import com.fpoliveira.atraso.feat_route_details.data.remote.ScheduleApi
import com.fpoliveira.atraso.feat_route_details.domain.model.ScheduleInfo
import com.fpoliveira.atraso.feat_route_details.domain.repository.ScheduleRepository
import com.fpoliveira.atraso.feat_route_details.domain.util.Resource
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ScheduleRepositoryImpl (
    context: Context,
    private val api: ScheduleApi
): ScheduleRepository, BaseRepository(context) {
    override suspend fun getSchedule(trainNumber: String, searchDate: ZonedDateTime): Resource<ScheduleInfo> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return when(val result = safeApiCall { api.getTrainSchedule(trainNumber.toString(), searchDate.format(formatter)) }) {
            is Resource.Success -> Resource.Success(data = result.data!!.response.toScheduleInfo(trainNumber))
            is Resource.Loading -> Resource.Loading()
            is Resource.Error -> Resource.Error(errorMessage = result.message!!)
        }
    }
}