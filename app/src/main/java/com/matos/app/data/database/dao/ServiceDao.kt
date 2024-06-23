package com.matos.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.matos.app.data.entity.Service

@Dao
interface ServiceDao {
    @Insert
    suspend fun insertService(service: Service)

    @Query("SELECT * FROM services WHERE client_id = :clientId")
    suspend fun getServicesForClient(clientId: Int): List<Service>

    @Query("SELECT * FROM services")
    suspend fun getServices(): List<Service>

    @Query("SELECT * FROM services WHERE description LIKE :query")
    suspend fun searchServices(query: String): List<Service>
}