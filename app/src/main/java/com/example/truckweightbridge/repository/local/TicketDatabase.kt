package com.example.truckweightbridge.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Ticket::class], version = 1)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
}