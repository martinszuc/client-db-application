package com.matos.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.matos.app.data.entity.Client

@Dao
interface ClientDao {
    @Insert
    suspend fun insertClient(client: Client): Long

    @Query("SELECT * FROM clients")
    suspend fun getClients(): List<Client>

    @Query("SELECT * FROM clients WHERE name LIKE :query")
    suspend fun searchClients(query: String): List<Client>
}