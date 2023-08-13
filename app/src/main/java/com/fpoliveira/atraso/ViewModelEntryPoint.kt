package com.fpoliveira.atraso

import android.content.Context
import com.fpoliveira.atraso.feat_home.presentation.HomeScreenViewModel
import com.fpoliveira.atraso.feat_route_details.presentation.TrainScheduleDetailsViewModel
import com.fpoliveira.atraso.feat_route_details.presentation.route_details_map.TrainScheduleDetailsMapViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ViewModelEntryPoint {
    fun homeScreenViewModel(): HomeScreenViewModel
    fun trainScheduleDetailsViewModelFactory(): TrainScheduleDetailsViewModel.Factory

    fun trainScheduleDetailsMapViewModelFactory(): TrainScheduleDetailsMapViewModel.Factory
}

val Context.viewModelEntryPoint: ViewModelEntryPoint
    get() = EntryPointAccessors.fromApplication(this)
