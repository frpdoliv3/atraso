package com.fpoliveira.atraso.feat_route_details.use_case

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.fpoliveira.atraso.feat_route_details.domain.model.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FetchLocationUseCase(
    private val context: Context,
    private val locationClient: FusedLocationProviderClient,
) {
    operator fun invoke(): Flow<Location> = callbackFlow {
        val locationRequest = LocationRequest.Builder(5000)
            .setGranularity(Granularity.GRANULARITY_FINE)
            .build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.lastLocation
                locationResult.lastLocation?.let {
                    Log.d("LocationReport", it.toString())
                    trySend(Location(
                        lat = it.latitude,
                        long = it.longitude
                    ))
                }
            }
        }

        val hasPermission = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            locationClient.requestLocationUpdates(locationRequest, callback, Looper.getMainLooper())
        }
        awaitClose { locationClient.removeLocationUpdates(callback) }
    }
}
