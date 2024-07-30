package com.martinszuc.clientsapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service_categories")
data class ServiceCategory(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val emoji: String
)