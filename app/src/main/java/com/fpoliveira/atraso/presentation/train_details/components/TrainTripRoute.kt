package com.fpoliveira.atraso.presentation.train_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.TripOrigin
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.unit.dp
import com.fpoliveira.atraso.R
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TrainTripRoute(
    departureDate: ZonedDateTime,
    arrivalDate: ZonedDateTime,
    departureStation: String,
    arrivalStation: String,
    modifier: Modifier = Modifier
) {
    val iconTextSpacer = 8.dp

    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    Column(
        modifier = modifier
    ) {
        Row {
            Image(
                Icons.Outlined.TripOrigin,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(iconTextSpacer))
            Text(pluralStringResource(
                R.plurals.schedule_details_time_plural,
                departureDate.hour,
                departureStation,
                departureDate.format(formatter)
            ))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Image(
                Icons.Outlined.LocationOn,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(iconTextSpacer))
            Text(pluralStringResource(
                R.plurals.schedule_details_time_plural,
                arrivalDate.hour,
                arrivalStation,
                arrivalDate.format(formatter)
            ))
        }
    }
}