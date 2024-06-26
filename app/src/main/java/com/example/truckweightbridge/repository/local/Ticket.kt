package com.example.truckweightbridge.repository.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "ticket")
@Parcelize
data class Ticket(
    @PrimaryKey(autoGenerate = false)
    var id: Long = System.currentTimeMillis(),
    val dateTime:String,
    val licenseNumber:String,
    val driverName:String,
    val inboundWeight: String,
    val outboundWeight: String,
    val netWeight: String,
    var firestoreDocId:String = ""
) : Parcelable
