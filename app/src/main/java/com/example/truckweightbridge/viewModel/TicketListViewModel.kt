package com.example.truckweightbridge.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.truckweightbridge.datasource.local.Ticket
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

    private val _getTicketResponse= MutableLiveData<Response<List<Ticket>>>()
    val getTicketResponse: MutableLiveData<Response<List<Ticket>>> =  _getTicketResponse

    fun getTicket(){
        launch {
            val query = FirestoreQueryBuilder()
                .build()
            val result = ticketUseCase.getTicket(query)
            _getTicketResponse.postValue(result)
        }
    }

}