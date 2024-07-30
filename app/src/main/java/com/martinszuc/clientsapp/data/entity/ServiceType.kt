package com.martinszuc.clientsapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "service_types",
    foreignKeys = [ForeignKey(
        entity = ServiceCategory::class,
        parentColumns = ["id"],
        childColumns = ["category_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ServiceType(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val emoji: String,
    val category_id: Int
)