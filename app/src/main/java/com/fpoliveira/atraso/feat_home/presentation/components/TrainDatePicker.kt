package com.fpoliveira.atraso.feat_home.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fpoliveira.atraso.R
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TrainDatePicker(
    modifier: Modifier = Modifier,
    dateTime: ZonedDateTime,
    onUpdateDate: (ZonedDateTime) -> Unit
) {
    val openedDialog = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    val source = remember { MutableInteractionSource() }.also { interactionSource ->
        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect {
                if(it is PressInteraction.Release) {
                    openedDialog.value = true
                }
            }
        }
    }
    TextField(
        modifier = modifier,
        value = dateTime.format(DateTimeFormatter.ofPattern(stringResource(R.string.home_train_date_format))),
        onValueChange = {},
        label = { Text(stringResource(R.string.home_train_date_label)) },
        readOnly = true,
        interactionSource = source,
        leadingIcon = {
            Icon (
                imageVector = Icons.Filled.CalendarMonth,
                contentDescription = "" //No description because it's used for decorative purposes
            )
        }
    )
    if (openedDialog.value) {
        DatePickerDialog(
            onDismissRequest = { openedDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        openedDialog.value = false
                        onUpdateDate(datePickerState.toZonedDateTime())
                    }
                ) {
                    Text("Confirm")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

private fun DatePickerState.toZonedDateTime(): ZonedDateTime = Instant.ofEpochMilli(selectedDateMillis!!)
    .atZone(ZoneId.systemDefault())
