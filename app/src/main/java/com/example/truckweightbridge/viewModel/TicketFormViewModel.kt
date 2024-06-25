package com.example.truckweightbridge.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.example.truckweightbridge.datasource.local.Ticket
import com.example.truckweightbridge.usecase.TicketUseCase
import com.example.truckweightbridge.util.Response
import com.example.truckweightbridge.util.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketFormViewModel @Inject constructor(
    val ticketUseCase: TicketUseCase
) : BaseViewModel(Dispatchers.IO) {

    private val _addTicketResponse= MutableLiveData<Response<Boolean>>()
    val addTicketResponse =  _addTicketResponse

    private var _validationResult = mutableStateOf(ValidationResult())
    var validationResult = this._validationResult

    private var _isValid = MutableLiveData<Pair<Boolean, Ticket>>()
    var isValid = _isValid

    fun validate(
        ticket: Ticket
    ) {
        val isDateTimeValid = ticket.dateTime.isNotBlank()
        val isDriverNameValid = ticket.driverName.isNotBlank()
        val isLicenceNumberValid = ticket.licenseNumber.isNotBlank() && ticket.licenseNumber.all { it.isDigit() }
        val isInboundWeightValid = ticket.inboundWeight.isNotBlank() && ticket.inboundWeight.all { it.isDigit() }
        val isOutboundWeightValid = ticket.outboundWeight.isNotBlank() && ticket.outboundWeight.all { it.isDigit() }

        this.validationResult.value = ValidationResult(
            isDateTimeValid,
            isDriverNameValid,
            isLicenceNumberValid,
            isInboundWeightValid,
            isOutboundWeightValid
        )
        this._isValid.value = (isDateTimeValid && isDriverNameValid
                && isLicenceNumberValid && isInboundWeightValid
                && isOutboundWeightValid) to ticket
    }

    fun addTicket(ticket: Ticket){
        launch {
            val result = ticketUseCase.addTicket(ticket)
            _addTicketResponse.postValue(result)
        }
    }

    fun updateTicket(ticket: Ticket){
        launch {
            val result = ticketUseCase.updateTicket(ticket)
            _addTicketResponse.postValue(result)
        }
    }

}