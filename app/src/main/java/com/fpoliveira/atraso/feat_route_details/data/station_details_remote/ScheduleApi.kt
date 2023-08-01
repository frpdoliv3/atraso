package com.fpoliveira.atraso.feat_route_details.data.station_details_remote

import com.fpoliveira.atraso.feat_route_details.data.schedule_remote.ScheduleDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ScheduleApi {
    @GET("{trainNumber}/{date}")
    suspend fun getTrainSchedule(
        @Path("trainNumber") trainNumber: String,
        @Path("date") date: String
    ): Response<ScheduleDto>
}