package com.fpoliveira.atraso.data.remote

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