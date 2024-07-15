package com.matos.app.ui.viewmodel

import com.matos.app.data.entity.Service
import com.matos.app.data.repository.ServiceRepository
import com.matos.app.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedServiceViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository
) : BaseViewModel() {

    private val _services = MutableStateFlow<List<Service>>(emptyList())
    val services: StateFlow<List<Service>> = _services.asStateFlow()

    fun loadServices() {
        launchDataLoad(
            execution = {
                serviceRepository.getServices()
            },
            onSuccess = { servicesList ->
                _services.value = servicesList.sortedByDescending { it.id } // Sort by descending order
            },
            onFailure = {
                // Handle the failure
            }
        )
    }

    fun addService(service: Service) {
        launchDataLoad(
            execution = {
                serviceRepository.insertService(service)
                serviceRepository.getServices() // Ensure this returns the updated list
            },
            onSuccess = { servicesList ->
                _services.value = servicesList.sortedByDescending { it.id } // Sort by descending order
            },
            onFailure = {
                // Handle the failure
            }
        )
    }
}