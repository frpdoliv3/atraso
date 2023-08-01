package com.fpoliveira.atraso.feat_route_details.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface StationDetailsApi {
    @POST("stations")
    suspend fun getStationDetails(
        @Body stations: List<SearchLocationDataDto>
    ): Response<List<StationDto>>

}