package com.martinszuc.clientsapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

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