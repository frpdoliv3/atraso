package com.fpoliveira.atraso.feat_route_details.use_case

import com.fpoliveira.atraso.feat_route_details.domain.model.ScheduleInfo
import com.fpoliveira.atraso.feat_route_details.domain.repository.ScheduleRepository
import com.fpoliveira.atraso.feat_route_details.domain.repository.StationDetailsRepository
import com.fpoliveira.atraso.feat_route_details.domain.util.Resource
import java.time.ZonedDateTime

class GetRouteDetailsUseCase(
    private val scheduleRepository: ScheduleRepository,
    private val stationRepository: StationDetailsRepository
) {
    suspend operator fun invoke(trainNumber: String, searchDate: ZonedDateTime): Resource<ScheduleInfo> {
        val scheduleData = scheduleRepository.getSchedule(trainNumber, searchDate)
        if (scheduleData !is Resource.Success) {
            return scheduleData
        }
        val unwrappedScheduleData = scheduleData.data!!
        val stationDetails = stationRepository
            .getStationDetails(unwrappedScheduleData.trainPassageNodes)
        if (stationDetails !is Resource.Success) {
            return scheduleData
        }
        return Resource.Success(unwrappedScheduleData.copy(
            trainPassageNodes = stationDetails.data!!
        ))
    }
}