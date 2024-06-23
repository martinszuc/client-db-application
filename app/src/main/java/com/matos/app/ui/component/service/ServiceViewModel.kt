package com.matos.app.ui.component.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matos.app.data.entity.Service
import com.matos.app.data.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository
) : ViewModel() {

    fun addService(service: Service) {
        viewModelScope.launch {
            serviceRepository.insertService(service)
        }
    }
}