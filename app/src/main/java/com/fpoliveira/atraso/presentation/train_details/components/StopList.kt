package com.fpoliveira.atraso.presentation.train_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fpoliveira.atraso.domain.model.StopData
import com.fpoliveira.atraso.presentation.ui.theme.GreenEmerald


@Composable
fun StopList(
    trainStopNodes: List<StopData>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(trainStopNodes.size) { stopIdx ->
            val stop = trainStopNodes[stopIdx]
            Stop(
                modifier = Modifier.fillMaxWidth(),
                stopData = stop
            )
            if (stopIdx + 1 != trainStopNodes.size) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(
                        modifier = Modifier
                            .width(1.dp)
                            .height(20.dp)
                            .background(
                                color = if (stop.trainPassed) { GreenEmerald } else { Color.LightGray }
                            )
                    )
                }
            }
        }
    }
}