package com.example.truckweightbridge.repository.remote

import com.example.truckweightbridge.util.Fields
import com.example.truckweightbridge.util.FirestoreDocumentResponse
import com.example.truckweightbridge.util.FirestoreQueryRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface FirebaseDatabaseApi {

    @POST("documents/truckweightbridge")
    suspend fun insertTicket(
        @Body fields: Map<String, Fields>
    ): Response<Map<String, Any>>

    @PATCH("documents/truckweightbridge/{documentId}")
    suspend fun updateTicket(
        @Path("documentId") documentId: String,
        @Body document: Map<String, Fields>
    ): Response<Map<String, Any>>

    @DELETE("documents/truckweightbridge/{documentId}")
    suspend fun deleteTicket(
        @Path("documentId") documentId: String,
    ): Response<Void>

    @POST("./documents:runQuery")
    suspend fun getTicket(
        @Body request: FirestoreQueryRequest
    ): List<FirestoreDocumentResponse>?
}