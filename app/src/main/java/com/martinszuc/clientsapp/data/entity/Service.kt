package com.martinszuc.clientsapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "services",
    foreignKeys = [
        ForeignKey(
            entity = Client::class,
            parentColumns = ["id"],
            childColumns = ["client_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ServiceCategory::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.SET_NULL  // Choose the appropriate behavior
        ),
        ForeignKey(
            entity = ServiceType::class,
            parentColumns = ["id"],
            childColumns = ["type_id"],
            onDelete = ForeignKey.SET_NULL  // Choose the appropriate behavior
        )
    ]
)
data class Service(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val client_id: Int,
    val description: String,
    val date: Date,
    val price: Double,
    val category_id: Int?,
    val type_id: Int?
)
