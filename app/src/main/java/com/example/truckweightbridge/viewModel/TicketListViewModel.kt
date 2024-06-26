package com.example.truckweightbridge.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.truckweightbridge.repository.local.Ticket
import com.example.truckweightbridge.usecase.TicketUseCase
import com.example.truckweightbridge.util.FirestoreQueryBuilder
import com.example.truckweightbridge.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketListViewModel @Inject constructor(
    val ticketUseCase: TicketUseCase
) : BaseViewModel(Dispatchers.IO) {

    private val _getTicketResponse = MutableLiveData<Response<List<Ticket>>>()
    val getTicketResponse: MutableLiveData<Response<List<Ticket>>> = _getTicketResponse

    fun getTicket(dateTime:String? = null,driverName: String? = null, licenseNumber:String?=null) {
        launch {
            val query = FirestoreQueryBuilder()

            dateTime?.let {
                query.where("dateTime","GREATER_THAN_OR_EQUAL", dateTime)
            }

            driverName?.let {

                query.where("driverName","GREATER_THAN_OR_EQUAL", driverName)
            }

            licenseNumber?.let {
                query.where("licenseNumber","EQUAL", licenseNumber)
            }

            val result = ticketUseCase.getTicket(query.build())
            _getTicketResponse.postValue(result)
        }
    }

}