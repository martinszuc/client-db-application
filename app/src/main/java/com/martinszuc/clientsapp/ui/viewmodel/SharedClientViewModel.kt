package com.martinszuc.clientsapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.martinszuc.clientsapp.data.entity.Client
import com.martinszuc.clientsapp.data.repository.ClientRepository
import com.martinszuc.clientsapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedClientViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : BaseViewModel() {

    private val _clients = MutableStateFlow<List<Client>>(emptyList())
    val clients: StateFlow<List<Client>> = _clients.asStateFlow()

    private val _selectedClient = MutableLiveData<Client>()
    val selectedClient: LiveData<Client> get() = _selectedClient

    fun selectClient(client: Client) {
        _selectedClient.value = client
    }

    fun loadClients() {
        launchDataLoad(
            execution = {
                clientRepository.getClients()
            },
            onSuccess = { clientsList ->
                _clients.value = clientsList.sortedByDescending { it.id } // Sort by descending order
            },
            onFailure = {
                // Handle the failure
            }
        )
    }

    fun addClient(client: Client) {
        launchDataLoad(
            execution = {
                clientRepository.insertClient(client)
                clientRepository.getClients() // Ensure this returns the updated list
            },
            onSuccess = { clientsList ->
                _clients.value = clientsList.sortedByDescending { it.id } // Sort by descending order
            },
            onFailure = {
                // Handle the failure
            }
        )
    }
}
