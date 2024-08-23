package com.martinszuc.clientsapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Project: database application
 *
 * Author: Bc. Martin Szuc (matoszuc@gmail.com)
 * GitHub: https://github.com/martinszuc
 *
 *
 * License:
 * This code is licensed under MIT License. You may not use this file except
 * in compliance with the License.
 */

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