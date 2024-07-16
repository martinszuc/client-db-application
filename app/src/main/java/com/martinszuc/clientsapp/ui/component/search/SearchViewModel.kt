package com.martinszuc.clientsapp.ui.component.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martinszuc.clientsapp.data.repository.ClientRepository
import com.martinszuc.clientsapp.data.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val clientRepository: ClientRepository,
    private val serviceRepository: ServiceRepository
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Any>>(emptyList())
    val searchResults: StateFlow<List<Any>> = _searchResults.asStateFlow()

    fun performSearch(query: String) {
        viewModelScope.launch {
            val clients = clientRepository.searchClients(query)
            val services = serviceRepository.searchServices(query)
            _searchResults.value = clients + services
        }
    }
}