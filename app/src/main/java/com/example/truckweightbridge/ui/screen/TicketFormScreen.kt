package com.example.truckweightbridge.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.truckweightbridge.repository.local.Ticket
import com.example.truckweightbridge.util.ValidationResult
import com.example.truckweightbridge.util.convertTimeMillisToString
import com.example.truckweightbridge.util.getCurrentDateTimeMillis
import com.example.truckweightbridge.util.getCurrentTimeUsingCalendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketFormScreen(
    onClickSave: (Ticket) -> Unit,
    onBackNavigation: () -> Unit,
    onClickDelete: (Ticket) -> Unit,
    existingTicket: Ticket? = null,
    isEdit: Boolean = false,
    validationResult: ValidationResult = ValidationResult()
) {

    val dateTimeMillis = getCurrentDateTimeMillis()
    val hours = getCurrentTimeUsingCalendar()

    var textDate by remember { mutableStateOf(dateTimeMillis.convertTimeMillisToString()) }
    var textTime by remember { mutableStateOf("${hours.first}:${hours.second} ${hours.third}") }
    var driverName by remember { mutableStateOf("") }
    var licenseNumber by remember { mutableStateOf("") }
    var inboundWeight by remember { mutableStateOf("") }
    var outboundWeight by remember { mutableStateOf("") }
    var nettWeight by remember { mutableStateOf("") }


    existingTicket?.let {
        textDate = it.dateTime.split("-").getOrNull(0).orEmpty()
        textTime = it.dateTime.split("-").getOrNull(1).orEmpty()
        driverName = it.driverName
        licenseNumber = it.licenseNumber
        inboundWeight = it.inboundWeight
        outboundWeight= it.outboundWeight
        nettWeight = it.netWeight
    }

    val showDialogDatePicker = rememberSaveable { mutableStateOf(false) }
    val showDialogTimePicker = rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    ShowDialogDatePicker(datePickerState, showDialogDatePicker) {time ->
        textDate = time.convertTimeMillisToString()
    }
    ShowDialogTimePicker(
        hours,
        showDialogTimePicker,
        onValueChange = { time: TimePickerState ->
            val amPm = if (time.hour < 12) "AM" else "PM"
            var hour = if (time.hour < 12) (time.hour).toString() else (time.hour - 12).toString()
            hour = if (hour.length == 1) "0$hour" else hour
            val minutes = if (time.minute.toString().length == 1) "0${time.minute}" else time.minute
            textTime = "$hour:$minutes $amPm"
        },
        onDismiss = {
            showDialogTimePicker.value = false
        })

    Scaffold(topBar = {
        TopAppBar(title =
        {
            Text(
                text = "Ticket",
                style = TextStyle(color = Color.Black, fontSize = 18.sp)
            )
        }, navigationIcon = {
            IconButton(onClick = {
                onBackNavigation.invoke()
            }) {
                Icon(Icons.Rounded.ArrowBack, "")
            }
        }, actions = {
//            if (isEdit) {
//                Icon(
//                    Icons.Outlined.Delete,
//                    contentDescription = "",
//                    modifier = Modifier
//                        .padding(10.dp)
//                        .clickable {
//                            existingTicket?.let {
//                                onClickDelete.invoke(it)
//                            }
//                        }
//                )
//            }
        })
    }, containerColor = Color.White) {
        Box(
            Modifier
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column {
                    Text("Date & Time", color = Color.Black)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            Modifier
                                .weight(1f)
                                .border(
                                    BorderStroke(1.dp, Color.Black),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .clickable {
                                    datePickerState.setSelection(dateTimeMillis)
                                    showDialogDatePicker.value = true
                                }) {
                            Text(
                                text = textDate,
                                modifier = Modifier.padding(12.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            Modifier
                                .weight(1f)
                                .border(
                                    BorderStroke(1.dp, Color.Black),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .clickable {
                                    showDialogTimePicker.value = true
                                }) {
                            Text(
                                text = textTime,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }

                if (!validationResult.isDateTimeValid) {
                    Text("Date Time is required", color = Color.Red)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = driverName,
                    onValueChange = { driverName = it },
                    label = { Text("Driver Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Black,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                if (!validationResult.isDriverNameValid) {
                    Text("Driver Name is required", color = Color.Red)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = licenseNumber,
                    onValueChange = { licenseNumber = it },
                    label = { Text("License Number") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Black,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                if (!validationResult.isLicenceNumberValid) {
                    Text("Licence Number must be a number and is required", color = Color.Red)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = inboundWeight,
                    onValueChange = {
                        inboundWeight = it
                        calculateNettWeight(inboundWeight, outboundWeight) {
                            nettWeight = it.toString()
                        }
                    },
                    label = { Text("Inbound Weight") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Black,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                if (!validationResult.isInboundWeightValid) {
                    Text("Inbound Weight must be a number and is required", color = Color.Red)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = outboundWeight,
                    onValueChange = {
                        outboundWeight = it
                        calculateNettWeight(inboundWeight, outboundWeight) {
                            nettWeight = it.toString()

                        }
                    },
                    label = { Text("Outbound Weight") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Black,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                if (!validationResult.isOutboundWeightValid) {
                    Text("Outbound Weight must be a number and is required", color = Color.Red)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = nettWeight,
                    onValueChange = { nettWeight = it },
                    label = { Text("Nett Weight") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    readOnly = true
                )

                Button(
                    onClick = {
                        onClickSave.invoke(
                            Ticket(
                                dateTime = "$textDate-$textTime",
                                licenseNumber = licenseNumber,
                                driverName = driverName,
                                inboundWeight = inboundWeight,
                                outboundWeight = outboundWeight,
                                netWeight = nettWeight
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors =  ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Save")
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDialogDatePicker(
    datePickerState: DatePickerState,
    showDialogDatePicker: MutableState<Boolean>,
    onChangeDate: (Long) -> Unit
) {
    if (showDialogDatePicker.value) {
        DatePickerDialog(
            onDismissRequest = { showDialogDatePicker.value = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialogDatePicker.value = false
                    onChangeDate(datePickerState.selectedDateMillis ?: 0)
                }) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialogDatePicker.value = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDialogTimePicker(
    currentHour: Triple<Int, Int, String>,
    isShow: MutableState<Boolean>, onDismiss: () -> Unit,
    onValueChange: (TimePickerState) -> Unit
) {

    var timePickerStateHorizontal = rememberTimePickerState(
        currentHour.first,
        currentHour.second, is24Hour = false
    )
    if (isShow.value) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimePicker(
                        state = timePickerStateHorizontal,
                        layoutType = TimePickerLayoutType.Vertical
                    )
                    Button(onClick = {
                        onDismiss.invoke()
                        onValueChange.invoke(timePickerStateHorizontal)
                        timePickerStateHorizontal = TimePickerState(
                            currentHour.first,
                            currentHour.second, is24Hour = false
                        )
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                        Text("OK")
                    }
                }
            }

        }
    }
}
fun calculateNettWeight(inboundWeight: String, outboundWeight: String, nettWeight: (Long) -> Unit) {
    if (inboundWeight.isNotEmpty() && outboundWeight.isNotEmpty()) {
        val inbound = inboundWeight.toLong()
        val outbound = outboundWeight.toLong()
        val nett = inbound - outbound
        nettWeight(nett)
    }
}


