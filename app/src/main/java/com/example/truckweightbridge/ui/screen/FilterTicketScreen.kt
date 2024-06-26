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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.truckweightbridge.util.convertTimeMillisToString
import com.example.truckweightbridge.util.getCurrentDateTimeMillis
import com.example.truckweightbridge.util.getCurrentTimeUsingCalendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterTicketScreen(
    onApplyFilter: (String, String, String) -> Unit,
    onClearFilter: () -> Unit,
    onBackNavigation: () -> Unit,
    existingFilterDate: String?,
    existingFilterDriverName: String?,
    existingFilterLicense: String?
) {

    val dateTimeMillis = getCurrentDateTimeMillis()

    var textDate by remember { mutableStateOf(existingFilterDate.orEmpty()) }
    var driverName by remember { mutableStateOf(existingFilterDriverName.orEmpty()) }
    var licenseNumber by remember { mutableStateOf(existingFilterLicense.orEmpty()) }

    val showDialogDatePicker = rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    ShowDialogDatePicker(datePickerState, showDialogDatePicker) { time ->
        textDate = time.convertTimeMillisToString()
    }


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
                    Text("Date", color = Color.Black)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        Modifier
                            .border(
                                BorderStroke(1.dp, Color.Black),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .clickable {
                                datePickerState.setSelection(dateTimeMillis)
                                showDialogDatePicker.value = true
                            }) {
                        Text(
                            text = if (textDate.isEmpty()) {
                                "Date"
                            } else {
                                textDate
                            },
                            modifier = Modifier.padding(12.dp)
                        )
                    }
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

                Button(
                    onClick = {
                        var dateTime = textDate
                        onApplyFilter.invoke(dateTime, driverName, licenseNumber)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Apply")
                }

                Button(
                    onClick = {
                        onClearFilter.invoke()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Clear Filter")
                }
            }
        }
    }

}


