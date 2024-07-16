package com.martinszuc.clientsapp.data.repository

import com.martinszuc.clientsapp.data.database.dao.ClientDao
import com.martinszuc.clientsapp.data.entity.Client
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientRepository @Inject constructor(private val clientDao: ClientDao) {
    suspend fun insertClient(client: Client) = clientDao.insertClient(client)
    suspend fun getClients() = clientDao.getClients()
    suspend fun searchClients(query: String) = clientDao.searchClients(query)
}