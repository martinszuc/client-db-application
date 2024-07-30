package com.martinszuc.clientsapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.martinszuc.clientsapp.data.entity.ServiceType

@Dao
interface ServiceTypeDao {
    @Query("SELECT * FROM service_types")
    suspend fun getAllServiceTypes(): List<ServiceType>

    @Insert
    suspend fun insertServiceType(type: ServiceType)
}