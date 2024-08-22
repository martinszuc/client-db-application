package com.martinszuc.clientsapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.martinszuc.clientsapp.data.entity.ServicePhoto

@Dao
interface ServicePhotoDao {
    @Insert
    suspend fun insertPhoto(servicePhoto: ServicePhoto)

    @Query("SELECT * FROM service_photos WHERE service_id = :serviceId")
    suspend fun getPhotosForService(serviceId: Int): List<ServicePhoto>
}
