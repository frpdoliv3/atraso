package com.fpoliveira.atraso.feat_route_details.data.repository

import android.content.Context
import com.fpoliveira.atraso.feat_route_details.data.schedule_remote.StationDetailsApi
import com.fpoliveira.atraso.feat_route_details.data.station_details_remote.SearchLocationDataDto
import com.fpoliveira.atraso.feat_route_details.domain.model.Location
import com.fpoliveira.atraso.feat_route_details.domain.model.StopData
import com.fpoliveira.atraso.feat_route_details.domain.repository.StationDetailsRepository
import com.fpoliveira.atraso.feat_route_details.domain.util.Resource

class StationDetailsRepositoryImpl(
    context: Context,
    private val api: StationDetailsApi
): StationDetailsRepository, BaseRepository(context) {
    override suspend fun getStationDetails(stops: List<StopData>): Resource<List<StopData>> {
        return when(
            val result = safeApiCall {
                api.getStationDetails(stops.map { it.toSearchLocationDataDto() })
            }
        ) {
            is Resource.Success -> {
                val stationDetails = result.data!!.associate {
                    it.externalId to StationDetails(
                        id = it.id,
                        name = it.name,
                        location = Location(lat = it.location.lat, long = it.location.long)
                    )
                }
                return Resource.Success(stops.map {
                    val details = stationDetails[it.externalId] ?: return Resource.Error(
                        errorMessage = "Unable to fetch details for: ${it.stationName}"
                    )
                    it.copy(
                        id = details.id,
                        location = details.location,
                        stationName = details.name
                    )
                })
            }
            is Resource.Loading -> Resource.Loading()
            is Resource.Error -> Resource.Error(errorMessage = result.message!!)
        }
    }

    private data class StationDetails(
        val id: Long,
        val name: String,
        val location: Location
    )
}

fun StopData.toSearchLocationDataDto() = SearchLocationDataDto(
    name = stationName,
    externalId = externalId
)
