package com.example.truckweightbridge.repository.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TicketDao {
    @Query("SELECT * FROM ticket ORDER BY dateTime")
    suspend fun getAllTicket(): List<Ticket>

    @Query("SELECT * FROM ticket WHERE dateTime BETWEEN :startDate AND :endDate AND driverName LIKE :driverName AND licenseNumber LIKE :licenseNumber")
    suspend fun getTicketByFilter(startDate: Long, endDate: Long, driverName: String, licenseNumber:Int): List<Ticket>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: Ticket)

    @Update
    suspend fun updateTicket(ticket: Ticket)

    @Delete
    suspend fun deleteTicket(ticket: Ticket)
}