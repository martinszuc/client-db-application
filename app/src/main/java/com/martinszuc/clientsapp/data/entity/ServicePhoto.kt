package com.martinszuc.clientsapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "service_photos",
    foreignKeys = [
        ForeignKey(
            entity = Service::class,
            parentColumns = ["id"],
            childColumns = ["service_id"],
            onDelete = ForeignKey.CASCADE  // When a service is deleted, its photos will be deleted too
        )
    ]
)
data class ServicePhoto(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val service_id: Int,  // Foreign key linking to the Service entity
    val photoUri: String  // URI for the photo
)