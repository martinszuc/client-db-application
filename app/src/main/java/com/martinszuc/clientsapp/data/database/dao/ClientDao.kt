package com.martinszuc.clientsapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.martinszuc.clientsapp.data.entity.Client
import java.util.Date

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
    @Query("UPDATE clients SET latestServiceDate = :date WHERE id = :clientId")
    suspend fun updateLatestServiceDate(clientId: Int, date: Date)
    @Query("DELETE FROM clients WHERE id = :clientId")
    suspend fun deleteClientById(clientId: Int)
}