package com.martinszuc.clientsapp.ui.viewmodel

import android.util.Log
import com.martinszuc.clientsapp.data.entity.Client
import com.martinszuc.clientsapp.data.repository.ClientRepository
import com.martinszuc.clientsapp.ui.base.BaseViewModel
import com.martinszuc.clientsapp.util.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SharedClientViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : BaseViewModel() {

    private val _clients = MutableStateFlow<List<Client>>(emptyList())
    val clients: StateFlow<List<Client>> = _clients.asStateFlow()

    private val _selectedClient = MutableStateFlow<Client?>(null)
    val selectedClient: StateFlow<Client?> = _selectedClient.asStateFlow()

    private val logTag = "SharedClientViewModel"

    fun selectClient(client: Client) {
        _selectedClient.value = client
    }

    fun loadClients() {
        Log.d(logTag, "Loading clients")
        launchDataLoad(
            execution = {
                Log.d(logTag, "Fetching clients from repository")
                clientRepository.getClients()
            },
            onSuccess = { clientsList ->
                Log.d(logTag, "Clients loaded successfully: $clientsList")
                _clients.value = clientsList.sortedByDescending { it.id }
            },
            onFailure = { e ->
                Log.e(logTag, "Failed to load clients", e)
            }
        )
    }

    fun addClient(client: Client) {
        Log.d(logTag, "Adding client: $client")
        launchDataLoad(
            execution = {
                clientRepository.insertClient(client)
                Log.d(logTag, "Client added successfully, reloading clients")
                clientRepository.getClients() // Ensure this returns the updated list
            },
            onSuccess = { clientsList ->
                Log.d(logTag, "Clients reloaded successfully: $clientsList")
                _clients.value = clientsList.sortedByDescending { it.id }
            },
            onFailure = { e ->
                Log.e(logTag, "Failed to add client", e)
            }
        )
    }

    fun getClientById(clientId: Int) {
        Log.d(logTag, "Loading client by ID: $clientId")
        launchDataLoad(
            execution = {
                clientRepository.getClientById(clientId)
            },
            onSuccess = { client ->
                if (client != null) {
                    Log.d(logTag, "Client loaded successfully: $client")
                    _selectedClient.value = client
                } else {
                    Log.e(logTag, "Client not found for ID: $clientId")
                    throw KotlinNullPointerException()
                }
            },
            onFailure = { e ->
                Log.e(logTag, "Failed to load client by ID", e)
            }
        )
    }

    fun updateLatestServiceDate(clientId: Int, date: Date) {
        Log.d(logTag, "Updating latest service date for client ID: $clientId to $date")
        launchDataLoad(
            execution = {
                clientRepository.updateLatestServiceDate(clientId, date)
                clientRepository.getClientById(clientId) // Ensure this returns the updated client
            },
            onSuccess = { client ->
                if (client != null) {
                    Log.d(logTag, "Client updated successfully: $client")
                    _selectedClient.value = client
                } else {
                    Log.e(logTag, "Client not found for ID: $clientId")
                    throw KotlinNullPointerException()
                }
            },
            onFailure = { e ->
                Log.e(logTag, "Failed to update latest service date", e)
            }
        )
    }
    suspend fun getClientName(clientId: Int): String {
        val client = _clients.value.find { it.id == clientId }
        return client?.name ?: AppConstants.UNKNOWN_CLIENT
    }
}