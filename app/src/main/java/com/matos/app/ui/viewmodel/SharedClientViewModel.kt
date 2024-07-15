package com.matos.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.matos.app.data.entity.Client
import com.matos.app.data.repository.ClientRepository
import com.matos.app.ui.base.AbstractViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedClientViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : AbstractViewModel() {

    private val _clients = MutableLiveData<List<Client>>()
    val clients: LiveData<List<Client>> get() = _clients

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
                _clients.value = clientsList
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
            },
            onSuccess = {
                loadClients()
            },
            onFailure = {
                // Handle the failure
            }
        )
    }
}
