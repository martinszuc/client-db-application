package com.matos.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matos.app.data.entity.Client
import com.matos.app.data.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedClientViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : ViewModel() {

    private val _clients = MutableLiveData<List<Client>>()
    val clients: LiveData<List<Client>> get() = _clients

    private val _selectedClient = MutableLiveData<Client>()
    val selectedClient: LiveData<Client> get() = _selectedClient

    fun selectClient(client: Client) {
        _selectedClient.value = client
    }

    fun loadClients() {
        viewModelScope.launch {
            _clients.value = clientRepository.getClients()
        }
    }

    fun addClient(client: Client) {
        viewModelScope.launch {
            clientRepository.insertClient(client)
            loadClients()
        }
    }
}