package com.matos.app.ui.component.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matos.app.data.repository.ClientRepository
import com.matos.app.data.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val clientRepository: ClientRepository,
    private val serviceRepository: ServiceRepository
) : ViewModel() {

    private val _searchResults = MutableLiveData<List<Any>>()
    val searchResults: LiveData<List<Any>> get() = _searchResults

    fun performSearch(query: String) {
        viewModelScope.launch {
            val clients = clientRepository.searchClients(query)
            val services = serviceRepository.searchServices(query)
            _searchResults.value = clients + services
        }
    }
}