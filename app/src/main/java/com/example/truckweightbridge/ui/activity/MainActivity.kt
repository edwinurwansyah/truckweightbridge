package com.example.truckweightbridge.ui.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import com.example.truckweightbridge.repository.local.Ticket
import com.example.truckweightbridge.ui.screen.TicketListScreen
import com.example.truckweightbridge.util.Response
import com.example.truckweightbridge.viewModel.TicketListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: TicketListViewModel by viewModels()
    private var tickets = mutableStateOf<List<Ticket>>(emptyList())
    private var filterDate:String? = null
    private var filterDriverName:String? = null
    private var filterLicense:String? = null

    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }


    private  val startFilterForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            filterDate = data?.getStringExtra(FilterActivity.FLAG_DATE_TIME)
            filterDriverName = data?.getStringExtra(FilterActivity.FLAG_DRIVER_NAME)
            filterLicense = data?.getStringExtra(FilterActivity.FLAG_LICENSE_NUMBER)
            viewModel.getTicket(filterDate, filterDriverName, filterLicense)
        }
    }

    private  val startCreateEditForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.getTicket()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTicket(filterDate, filterDriverName, filterLicense)
        iniObserver()
        setContent {
            TicketListScreen(tickets = tickets.value, onClickCreate = {
                goToCreateEditScreen(false)
            }, onClickItem = {
                goToCreateEditScreen(true,it)
            }, onClickFilter = {
                goToFilter()
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
            startCreateEditForResult.launch(this)
        }
    }

    private fun goToFilter(){
        val intent = Intent(this, FilterActivity::class.java)
        intent.putExtra(FilterActivity.FLAG_DRIVER_NAME, filterDriverName)
        intent.putExtra(FilterActivity.FLAG_DATE_TIME, filterDate)
        intent.putExtra(FilterActivity.FLAG_LICENSE_NUMBER, filterLicense)
        filterDate = null
        filterDriverName = null
        filterLicense = null
        startFilterForResult.launch(intent)
    }

}

