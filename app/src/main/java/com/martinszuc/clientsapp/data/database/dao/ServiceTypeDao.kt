package com.martinszuc.clientsapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.martinszuc.clientsapp.data.entity.ServiceType

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

@Dao
interface ServiceTypeDao {
    @Query("SELECT * FROM service_types")
    suspend fun getAllServiceTypes(): List<ServiceType>

    @Insert
    suspend fun insertServiceType(type: ServiceType)
}