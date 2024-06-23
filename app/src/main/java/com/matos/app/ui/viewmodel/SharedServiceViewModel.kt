package com.matos.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matos.app.data.entity.Service
import com.matos.app.data.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedServiceViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository
) : ViewModel() {

    private val _services = MutableLiveData<List<Service>>()
    val services: LiveData<List<Service>> get() = _services

    fun loadServices() {
        viewModelScope.launch {
            _services.value = serviceRepository.getServices()
        }
    }

    fun addService(service: Service) {
        viewModelScope.launch {
            serviceRepository.insertService(service)
            loadServices()
        }
    }
}