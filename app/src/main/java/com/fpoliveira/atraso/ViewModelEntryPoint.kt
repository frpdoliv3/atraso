package com.fpoliveira.atraso

import android.content.Context
import com.fpoliveira.atraso.presentation.home_screen.HomeScreenViewModel
import com.fpoliveira.atraso.presentation.train_details.TrainScheduleDetailsViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ViewModelEntryPoint {
    fun homeScreenViewModel(): HomeScreenViewModel
    fun trainScheduleDetailsViewModelFactory(): TrainScheduleDetailsViewModel.Factory
}

val Context.viewModelEntryPoint: ViewModelEntryPoint
    get() = EntryPointAccessors.fromApplication(this)