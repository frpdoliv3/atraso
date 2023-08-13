package com.fpoliveira.atraso.feat_route_details.presentation.route_details_map.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.fpoliveira.atraso.R
import com.fpoliveira.atraso.feat_route_details.domain.model.StopData
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun StopSheetContent(
    stopData: StopData?,
    modifier: Modifier = Modifier
) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val detailsModifier = Modifier.padding(start = 24.dp)


    if (stopData != null) {
        Column(modifier = modifier) {
            Text(
                stopData.stationName,
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(stringResource(R.string.schedule_details_station_departure_date))
                    }
                    append(" ")
                    append(formatter.format(stopData.scheduledTime))
                },
                modifier = detailsModifier
            )
            stopData.location?.let { stopLocation ->
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(stringResource(R.string.schedule_details_location))
                        }
                        append(" ")
                        append(stopLocation.lat.toString())
                        append(", ")
                        append(stopLocation.long.toString())
                    },
                    modifier = detailsModifier
                )
            }
            if (stopData.observations.isNotEmpty()) {
                Text(stringResource(R.string.app_observations),
                    fontWeight = FontWeight.Bold,
                    modifier = detailsModifier
                )
                Text(stopData.observations,
                    modifier = detailsModifier.padding(start = 8.dp)
                )
            }
        }
    }
}
