package com.matos.app.ui.component.client

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
class ClientViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : ViewModel() {

    private val _clients = MutableLiveData<List<Client>>()
    val clients: LiveData<List<Client>> get() = _clients

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