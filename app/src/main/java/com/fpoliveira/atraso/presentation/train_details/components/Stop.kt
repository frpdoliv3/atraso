package com.fpoliveira.atraso.presentation.train_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpoliveira.atraso.domain.model.StopData
import com.fpoliveira.atraso.presentation.ui.theme.GreenEmerald
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun Stop(
    stopData: StopData,
    modifier: Modifier = Modifier
) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    Row(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = stopData.stationName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        Image(
            imageVector = if (stopData.trainPassed) { Icons.Default.RadioButtonChecked } else { Icons.Default.RadioButtonUnchecked },
            contentDescription = null,
            colorFilter = if (stopData.trainPassed) { ColorFilter.tint(GreenEmerald) } else { ColorFilter.tint(Color.LightGray) }
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            text = stopData.scheduledTime.format(formatter),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun StopPreview() {
    Stop(
        StopData(
            "Lisboa - Santa Apolónia",
            true,
            LocalTime.now(),
            ""
        )
    )
}
