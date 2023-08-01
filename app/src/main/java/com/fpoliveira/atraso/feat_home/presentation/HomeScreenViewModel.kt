package com.fpoliveira.atraso.feat_home.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.ZonedDateTime
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(): ViewModel() {
    private val _trainNumberState = mutableStateOf("")
    val trainNumberState: State<String> = _trainNumberState

    private val _trainDateState: MutableState<ZonedDateTime> = mutableStateOf(ZonedDateTime.now())
    val trainDateState: State<ZonedDateTime> = _trainDateState

    fun onEvent(event: HomeScreenEvent) {
        when(event) {
            is HomeScreenEvent.OnTrainNumberChange -> {
                _trainNumberState.value = event.trainNumber
            }
            is HomeScreenEvent.OnTrainDateChange -> {
                _trainDateState.value = event.date
            }
        }
    }
}
