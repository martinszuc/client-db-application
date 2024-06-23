package com.matos.app.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "services",
    foreignKeys = [ForeignKey(
        entity = Client::class,
        parentColumns = ["id"],
        childColumns = ["client_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Service(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val client_id: Int,
    val description: String,
    val date: Date,
    val price: Double
)