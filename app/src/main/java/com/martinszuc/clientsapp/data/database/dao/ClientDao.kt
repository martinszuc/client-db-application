package com.martinszuc.clientsapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.martinszuc.clientsapp.data.entity.Client

@Dao
interface ClientDao {
    @Insert
    suspend fun insertClient(client: Client): Long

    @Query("SELECT * FROM clients")
    suspend fun getClients(): List<Client>

    @Query("SELECT * FROM clients WHERE name LIKE :query")
    suspend fun searchClients(query: String): List<Client>

    @Query("SELECT * FROM clients WHERE id = :clientId")
    suspend fun getClientById(clientId: Int): Client?
}