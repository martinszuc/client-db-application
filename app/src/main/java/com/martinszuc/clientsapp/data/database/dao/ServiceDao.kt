package com.martinszuc.clientsapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.martinszuc.clientsapp.data.entity.Service

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
interface ServiceDao {
    @Insert
    suspend fun insertService(service: Service): Long

    @Query("SELECT * FROM services WHERE client_id = :clientId")
    suspend fun getServicesForClient(clientId: Int): List<Service>

    @Query("SELECT * FROM services")
    suspend fun getServices(): List<Service>

    @Query("SELECT * FROM services WHERE description LIKE :query")
    suspend fun searchServices(query: String): List<Service>
    @Delete
    suspend fun deleteService(service: Service)
}