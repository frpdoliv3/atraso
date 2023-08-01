package com.fpoliveira.atraso.feat_route_details.presentation.train_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fpoliveira.atraso.R
import com.fpoliveira.atraso.feat_route_details.domain.model.ScheduleInfo
import com.fpoliveira.atraso.presentation.Screen
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.pop

@Composable
fun TrainScheduleDetails (
    navController: NavController<Screen>,
    scheduleInfo: ScheduleInfo,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.schedule_details_top_bar, scheduleInfo.trainNumber))},
                navigationIcon = {
                    if (navController.backstack.entries.isNotEmpty()) {
                        IconButton(onClick = { navController.pop() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                        }
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(
                    top = innerPadding.calculateTopPadding(),
                    start = 20.dp,
                    end = 20.dp,
                    bottom = innerPadding.calculateBottomPadding()
                )
            ) {
                TrainTripRoute(
                    scheduleInfo.departureDate,
                    scheduleInfo.arrivalDate,
                    scheduleInfo.departure,
                    scheduleInfo.destination
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (scheduleInfo.trainStatus.isNotEmpty()) {
                    Column {
                        Text(
                            stringResource(R.string.schedule_details_status),
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(scheduleInfo.trainStatus)
                    }
                }
                StopList(
                    modifier = Modifier.padding(vertical = 16.dp),
                    trainStopNodes = scheduleInfo.trainPassageNodes
                )
            }
        }
    )
}
