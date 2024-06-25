package com.example.truckweightbridge.usecase

import com.example.truckweightbridge.datasource.local.Ticket
import com.example.truckweightbridge.datasource.local.TicketDao
import com.example.truckweightbridge.datasource.remote.FirebaseDatabaseApi
import com.example.truckweightbridge.util.Fields
import com.example.truckweightbridge.util.FirestoreQueryRequest
import com.example.truckweightbridge.util.IntegerValue
import com.example.truckweightbridge.util.Response
import com.example.truckweightbridge.util.StringValue
import javax.inject.Inject

class TicketUseCaseImpl @Inject constructor(val firebaseDatabaseApi: FirebaseDatabaseApi, val ticketDao: TicketDao) : TicketUseCase {
    override suspend fun addTicket(ticket: Ticket): Response<Boolean> {
        return try {
            val fieldRequest = Fields(
                id = IntegerValue(ticket.id.toString()),
                driverName = StringValue(ticket.driverName),
                dateTime = StringValue(ticket.dateTime),
                inboundWeight = IntegerValue(ticket.inboundWeight),
                outboundWeight = IntegerValue(ticket.outboundWeight),
                netWeight = IntegerValue(ticket.netWeight),
                licenseNumber = IntegerValue(ticket.licenseNumber)
            )
            val request = mapOf("fields" to fieldRequest)
            val response = firebaseDatabaseApi.insertTicket(request)
            if (response.isSuccessful){
                ticket.firestoreDocId = response.body()?.getOrDefault("name","").toString().split("/").last()
                ticketDao.insertTicket(ticket)
            }
            Response.Success(response.isSuccessful)
        }catch (e:Exception){
            Response.Error(e.message.toString())
        }
    }

    override suspend fun updateTicket(ticket: Ticket): Response<Boolean> {
        return try {
            var fieldRequest = Fields(
                id = IntegerValue(ticket.id.toString()),
                driverName = StringValue(ticket.driverName),
                dateTime = StringValue(ticket.dateTime),
                inboundWeight = IntegerValue(ticket.inboundWeight),
                outboundWeight = IntegerValue(ticket.outboundWeight),
                netWeight = IntegerValue(ticket.netWeight),
                licenseNumber = IntegerValue(ticket.licenseNumber)
            )
            val request = mapOf("fields" to fieldRequest)
            val response = firebaseDatabaseApi.updateTicket(ticket.firestoreDocId,request)
            if (response.isSuccessful){
                ticketDao.updateTicket(ticket)
            }
            Response.Success(response.isSuccessful)
        }catch (e:Exception){
            Response.Error(e.message.toString())
        }
    }

    override suspend fun getTicket(
        firestoreQueryRequest: FirestoreQueryRequest
    ): Response<List<Ticket>> {
        return try {
            val response = firebaseDatabaseApi.getTicket(firestoreQueryRequest)
            return Response.Success(data = response.map { response ->
                Ticket(
                    id = (response.document.fields.id?.integerValue?:"0").toLong(),
                    dateTime = (response.document.fields.dateTime?.stringValue?:"0"),
                    licenseNumber = (response.document.fields.licenseNumber?.integerValue?:"0"),
                    driverName = response.document.fields.driverName?.stringValue.orEmpty(),
                    inboundWeight = (response.document.fields.inboundWeight?.integerValue?:"0"),
                    outboundWeight = (response.document.fields.outboundWeight?.integerValue?:"0"),
                    netWeight = (response.document.fields.netWeight?.integerValue?:"0"),
                    firestoreDocId = response.document.name.split("/").last()
                )
            })
        }catch (e:Exception){
            val dataLocal = ticketDao.getAllTicket()
            Response.Error(message = e.message.orEmpty(), data = dataLocal)
        }
    }
}