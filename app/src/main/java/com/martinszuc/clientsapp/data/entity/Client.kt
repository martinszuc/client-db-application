package com.martinszuc.clientsapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "clients")
data class Client(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val phone: String?,
    val email: String?,
    val profilePictureUrl: String? = null,
    val profilePictureColor: String? = null,
    val latestServiceDate: Date? = null
)