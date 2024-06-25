package com.example.truckweightbridge.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.truckweightbridge.datasource.local.Ticket
import com.example.truckweightbridge.ui.screen.TicketListScreen
import com.example.truckweightbridge.ui.theme.TruckWeightBridgeTheme
import com.example.truckweightbridge.util.Response
import com.example.truckweightbridge.viewModel.TicketFormViewModel
import com.example.truckweightbridge.viewModel.TicketListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: TicketListViewModel by viewModels()
    private var tickets = mutableStateOf<List<Ticket>>(emptyList())

    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iniObserver()
        setContent {
            TicketListScreen(tickets = tickets.value, onClickCreate = {
                goToCreateEditScreen(true)
            }, onClickItem = {
                goToCreateEditScreen(true,it)
            }, onClickFilter = {

            })
        }
    }

    private fun iniObserver(){
        viewModel.getTicketResponse.observe(this){
            when (it) {
                is Response.Success -> {
                    progressDialog.dismiss()
                    tickets.value = it.data.orEmpty()
                }

                is Response.Error -> {
                    progressDialog.dismiss()
                    if (it.data !=null){
                        tickets.value = it.data
                    }
                    Toast.makeText(this, "Gagal Mendapatkan", Toast.LENGTH_LONG).show()
                }

                else -> {
                    progressDialog.show()
                }
            }
        }
    }

    private fun goToCreateEditScreen(isEdit: Boolean, existingTicket: Ticket? = null) {
        Intent(this, TicketFormActivity::class.java).apply {
            putExtra(TicketFormActivity.IS_EDIT_FLAG, isEdit)
            putExtra(TicketFormActivity.EXISTING_TICKET_FLAG, existingTicket)
            startActivity(this)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTicket()
    }
}

