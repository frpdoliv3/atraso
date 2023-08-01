package com.fpoliveira.atraso.feat_route_details.data.schedule_remote

import com.fpoliveira.atraso.feat_route_details.data.station_details_remote.SearchLocationDataDto
import com.fpoliveira.atraso.feat_route_details.data.station_details_remote.StationDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface StationDetailsApi {
    @POST("stations")
    suspend fun getStationDetails(
        @Body stations: List<SearchLocationDataDto>
    ): Response<List<StationDto>>

}