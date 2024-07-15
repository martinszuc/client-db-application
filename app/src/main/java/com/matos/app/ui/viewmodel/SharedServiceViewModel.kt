package com.matos.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.matos.app.data.entity.Service
import com.matos.app.data.repository.ServiceRepository
import com.matos.app.ui.base.AbstractViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedServiceViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository
) : AbstractViewModel() {

    private val _services = MutableLiveData<List<Service>>()
    val services: LiveData<List<Service>> get() = _services

    fun loadServices() {
        launchDataLoad(
            execution = {
                serviceRepository.getServices()
            },
            onSuccess = { servicesList ->
                _services.value = servicesList
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
            },
            onSuccess = {
                loadServices()
            },
            onFailure = {
                // Handle the failure
            }
        )
    }
}
