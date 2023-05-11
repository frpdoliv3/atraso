package com.fpoliveira.atraso.presentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fpoliveira.atraso.R
import com.fpoliveira.atraso.presentation.Screen
import com.fpoliveira.atraso.presentation.home_screen.components.TrainDatePicker
import com.fpoliveira.atraso.viewModelEntryPoint
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.navigate

@Composable
fun HomeScreen(
    navController: NavController<Screen>
) {
    val viewModelEntryPoint = LocalContext.current.viewModelEntryPoint
    val viewModel = viewModel {
        viewModelEntryPoint.homeScreenViewModel()
    }
    val trainNumberState = viewModel.trainNumberState.value
    val trainDateState = viewModel.trainDateState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = trainNumberState,
            onValueChange = { viewModel.onEvent(HomeScreenEvent.OnTrainNumberChange(it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            label = { Text(stringResource(R.string.home_train_num_label)) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TrainDatePicker(
            modifier = Modifier.fillMaxWidth(),
            dateTime = trainDateState
        ) {
            viewModel.onEvent(HomeScreenEvent.OnTrainDateChange(it))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier.align(alignment = Alignment.End),
            onClick = { navController.navigate(Screen.TrainScheduleDetailsScreen(trainNumberState, trainDateState)) }
        ) {
            Text(stringResource(R.string.home_train_search_action))
        }
    }
}
