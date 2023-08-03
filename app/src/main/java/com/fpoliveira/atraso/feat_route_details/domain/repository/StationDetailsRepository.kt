package com.fpoliveira.atraso.feat_route_details.domain.repository

import com.fpoliveira.atraso.feat_route_details.domain.model.StopData
import com.fpoliveira.atraso.feat_route_details.domain.util.Resource

interface StationDetailsRepository {
    suspend fun getStationDetails(stops: List<StopData>): Resource<List<StopData>>
}
