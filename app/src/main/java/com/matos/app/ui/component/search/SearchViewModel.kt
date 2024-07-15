package com.matos.app.ui.component.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.matos.app.data.repository.ClientRepository
import com.matos.app.data.repository.ServiceRepository
import com.matos.app.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val clientRepository: ClientRepository,
    private val serviceRepository: ServiceRepository
) : BaseViewModel() {

    private val _searchResults = MutableLiveData<List<Any>>()
    val searchResults: LiveData<List<Any>> get() = _searchResults

    fun performSearch(query: String) {
        launchDataLoad(
            execution = {
                val clients = clientRepository.searchClients(query)
                val services = serviceRepository.searchServices(query)
                clients + services
            },
            onSuccess = { results ->
                _searchResults.value = results
            },
            onFailure = { e ->
                // Handle the failure
            }
        )
    }
}
