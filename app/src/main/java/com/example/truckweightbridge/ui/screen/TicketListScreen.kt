package com.example.truckweightbridge.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.truckweightbridge.R
import com.example.truckweightbridge.datasource.local.Ticket
import com.example.truckweightbridge.ui.theme.TruckWeightBridgeTheme
import com.example.truckweightbridge.util.ValidationResult


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketListScreen(tickets: List<Ticket>, onClickCreate: () -> Unit,
                     onClickFilter: () -> Unit, onClickItem: (Ticket) -> Unit) {
    TruckWeightBridgeTheme {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(
                    text = "TodoList", style = TextStyle(color = Color.Black, fontSize = 18.sp)
                )
            }, actions = {
                Icon(
                    painterResource(R.drawable.ic_baseline_filter_list_24),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            onClickFilter.invoke()
                        })
                Icon(
                    painterResource(R.drawable.ic_baseline_add_24),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            onClickCreate.invoke()
                        })
            })
        }, containerColor = Color.Gray) {
            Column(
                Modifier.padding(it)
            ) {
                LazyColumn(
                    Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    items(tickets.size) {
                        val item = tickets[it]
                        ItemTicket(ticket = item) {
                            onClickItem.invoke(it)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ItemTicket(ticket: Ticket, onClickItem: (Ticket) -> Unit) {
    Card(shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        modifier = Modifier
            .padding(bottom = 8.dp)
            .clickable {
                onClickItem.invoke(ticket)
            }) {
        Column(
            Modifier
                .background(Color.White)
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = ticket.driverName,
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = ticket.dateTime, fontSize = 12.sp,
                    modifier = Modifier.weight(1f), style = TextStyle(textAlign = TextAlign.End)
                )
            }
            Text(text = "Nett Weight: ${ticket.netWeight}", fontSize = 10.sp)
        }

    }
}