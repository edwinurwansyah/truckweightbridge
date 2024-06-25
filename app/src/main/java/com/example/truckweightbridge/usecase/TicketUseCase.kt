package com.example.truckweightbridge.usecase

import com.example.truckweightbridge.datasource.local.Ticket
import com.example.truckweightbridge.util.FirestoreQueryRequest
import com.example.truckweightbridge.util.Response

interface TicketUseCase {
    suspend fun addTicket(ticket: Ticket): Response<Boolean>
    suspend fun updateTicket(ticket: Ticket): Response<Boolean>
    suspend fun getTicket(
       firestoreQueryRequest: FirestoreQueryRequest
    ): Response<List<Ticket>>
}