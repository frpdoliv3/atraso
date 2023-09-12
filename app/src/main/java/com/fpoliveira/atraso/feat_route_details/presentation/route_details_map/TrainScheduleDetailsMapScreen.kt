package com.fpoliveira.atraso.feat_route_details.presentation.route_details_map

import android.Manifest
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fpoliveira.atraso.R
import com.fpoliveira.atraso.feat_route_details.domain.model.Location
import com.fpoliveira.atraso.feat_route_details.domain.model.ScheduleInfo
import com.fpoliveira.atraso.feat_route_details.domain.model.StopData
import com.fpoliveira.atraso.feat_route_details.presentation.route_details_map.components.StopSheetContent
import com.fpoliveira.atraso.presentation.Screen
import com.fpoliveira.atraso.viewModelEntryPoint
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.pop
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun fetchStopIcon(idx: Int, stopData: StopData, size: Int) = when (idx) {
    0 -> {
        if (stopData.trainPassed) {
            BitmapDescriptorFactory.fromResource(R.drawable.ic_origin_arrived)
        } else {
            BitmapDescriptorFactory.fromResource(R.drawable.ic_origin_not_arrived)
        }
    }
    size - 1 -> {
        if (stopData.trainPassed) {
            BitmapDescriptorFactory.fromResource(R.drawable.ic_destination_arrived)
        } else {
            BitmapDescriptorFactory.fromResource(R.drawable.ic_destination_not_arrived)
        }
    }
    else -> {
        if (stopData.trainPassed) {
            BitmapDescriptorFactory.fromResource(R.drawable.ic_intermidiate_arrived)
        } else {
            BitmapDescriptorFactory.fromResource(R.drawable.ic_intermidiate_not_arrived)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TrainScheduleDetailsMapScreen(
    navController: NavController<Screen>,
    scheduleInfo: ScheduleInfo,
) {
    val cameraAnimationSpeed = 500
    val cameraAnimationZoom = 13.5F

    val viewModelEntryPoint = LocalContext.current.viewModelEntryPoint
    val appContext = LocalContext.current.applicationContext
    val viewModel: TrainScheduleDetailsMapViewModel = viewModel {
        viewModelEntryPoint.trainScheduleDetailsMapViewModelFactory().create(
            appContext,
            scheduleInfo
        )
    }

    val locationPermissionState = rememberMultiplePermissionsState(listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    ))

    val cameraPositionState = rememberCameraPositionState()

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )

    val lastArrivalStation = scheduleInfo.trainPassageNodes.find { !it.trainPassed } ?:
        scheduleInfo.trainPassageNodes.last()

    LaunchedEffect(Unit) {
        locationPermissionState.launchMultiplePermissionRequest()
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(
                lastArrivalStation.location!!.toLatLng(),
                cameraAnimationZoom
            ),
            cameraAnimationSpeed
        )
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is TrainScheduleDetailsMapViewModel.UiEvent.BottomSheetDataChange -> {
                    if (scaffoldState.bottomSheetState.hasExpandedState) {
                        scaffoldState.bottomSheetState.hide()
                    }
                    scaffoldState.bottomSheetState.expand()
                }
                is TrainScheduleDetailsMapViewModel.UiEvent.HideBottomSheet -> {
                    scaffoldState.bottomSheetState.hide()
                }
            }
        }
    }

    val grantedPermissions = locationPermissionState.permissions
        .map { it.status.isGranted }
        .filter { it }

    if (locationPermissionState.shouldShowRationale) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Outlined.Explore,
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
            )
            Text(text = stringResource(R.string.schedule_details_location_rationale))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = { locationPermissionState.launchMultiplePermissionRequest() }
            ) {
                Text(text = stringResource(R.string.app_continue))
            }
        }
    } else {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = { StopSheetContent(
                viewModel.selectedStopState.value,
                modifier = Modifier.padding(16.dp)
            ) }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                GoogleMap(
                    uiSettings = MapUiSettings(
                        compassEnabled = false,
                        mapToolbarEnabled = false,
                        myLocationButtonEnabled = false,
                        zoomControlsEnabled = false
                    ),
                    cameraPositionState = cameraPositionState,
                    onMapClick = { viewModel.onEvent(TrainScheduleDetailsMapEvent.DismissEvent) }
                ) {
                    scheduleInfo.trainPassageNodes.mapIndexed { idx, stopData ->
                        val icon = fetchStopIcon(idx, stopData, scheduleInfo.trainPassageNodes.size)
                        Marker(
                            icon = icon,
                            flat = true,
                            state = MarkerState(position = stopData.location!!.toLatLng()),
                            onClick = {
                                scope.launch { cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(
                                        stopData.location.toLatLng(),
                                        cameraAnimationZoom
                                    ),
                                    cameraAnimationSpeed
                                ) }
                                viewModel.onEvent(TrainScheduleDetailsMapEvent.SelectedStopEvent(stopData))
                                true
                            }
                        )
                        /*userLocation?.let {
                            Marker(
                                icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location),
                                state = MarkerState(position = LatLng(it.lat, it.long))
                            )
                        }*/
                    }
                }
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp) //Used to make bar distant from the parent
                        .padding(top = 24.dp)
                        .clip(RoundedCornerShape(48.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    IconButton(onClick = { navController.pop() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .padding(vertical = 4.dp),
                        color = Color.Black
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 4.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(viewModel.nextStopNotice.value)
                        if (scheduleInfo.trainStatus.isNotEmpty()) {
                            Text(scheduleInfo.trainStatus)
                        }
                    }
                }
            }
        }
    }
}

fun Location.toLatLng(): LatLng = LatLng(lat, long)
