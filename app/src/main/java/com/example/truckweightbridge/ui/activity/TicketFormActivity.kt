package com.example.truckweightbridge.ui.activity

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.truckweightbridge.repository.local.Ticket
import com.example.truckweightbridge.ui.screen.TicketFormScreen
import com.example.truckweightbridge.ui.theme.TruckWeightBridgeTheme
import com.example.truckweightbridge.util.Response
import com.example.truckweightbridge.viewModel.TicketFormViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketFormActivity : ComponentActivity() {

    companion object {
        val IS_EDIT_FLAG = "is_edit"
        val EXISTING_TICKET_FLAG = "exiting_ticket"
    }

    private val viewModel: TicketFormViewModel by viewModels()

    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    val isEdit: Boolean by lazy {
        intent.getBooleanExtra(IS_EDIT_FLAG, false)
    }

    val ticket:Ticket? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXISTING_TICKET_FLAG, Ticket::class.java)
        } else {
            intent.getParcelableExtra(EXISTING_TICKET_FLAG)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
        setContent {
            TruckWeightBridgeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicketFormScreen(onClickSave = {
                        viewModel.validate(
                            it
                        )
                    }, onBackNavigation = {

                    }, onClickDelete = {

                    }, validationResult = viewModel.validationResult.value,
                        isEdit = isEdit,
                        existingTicket = ticket)
                }
            }
        }

    }

    private fun initObserver() {
        viewModel.addTicketResponse.observe(this) {
            when (it) {
                is Response.Success -> {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Ticket Berhasil Ditambahkan", Toast.LENGTH_LONG).show()
                    setResult(RESULT_OK)
                    finish()
                }

                is Response.Error -> {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Ticket Gagal Ditambahkan", Toast.LENGTH_LONG).show()
                }

                else -> {
                    progressDialog.show()
                }
            }
        }

        viewModel.isValid.observe(this) {
            if (it.first) {
                if (isEdit){
                    it.second.firestoreDocId = ticket?.firestoreDocId.orEmpty()
                    viewModel.updateTicket(it.second)
                }else{
                    viewModel.addTicket(it.second)
                }
            }
        }
    }
}
