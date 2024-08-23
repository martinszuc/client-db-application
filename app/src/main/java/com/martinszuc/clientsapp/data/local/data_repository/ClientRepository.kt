package com.martinszuc.clientsapp.data.local.data_repository

import com.martinszuc.clientsapp.data.database.dao.ClientDao
import com.martinszuc.clientsapp.data.entity.Client
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientRepository @Inject constructor(
    private val clientDao: ClientDao
) {
    suspend fun insertClient(client: Client) = clientDao.insertClient(client)
    suspend fun getClients() = clientDao.getClients()
    suspend fun searchClients(query: String) = clientDao.searchClients(query)
    suspend fun getClientById(clientId: Int) = clientDao.getClientById(clientId)
    suspend fun updateLatestServiceDate(clientId: Int, date: Date) = clientDao.updateLatestServiceDate(clientId, date)
    suspend fun deleteClientById(clientId: Int) = clientDao.deleteClientById(clientId)

}