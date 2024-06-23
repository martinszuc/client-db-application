package com.matos.app.data.repository

import com.matos.app.data.database.dao.ClientDao
import com.matos.app.data.entity.Client
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientRepository @Inject constructor(private val clientDao: ClientDao) {
    suspend fun insertClient(client: Client) = clientDao.insertClient(client)
    suspend fun getClients() = clientDao.getClients()
    suspend fun searchClients(query: String) = clientDao.searchClients(query)
}