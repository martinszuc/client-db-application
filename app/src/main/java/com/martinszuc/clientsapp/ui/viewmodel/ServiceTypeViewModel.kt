package com.martinszuc.clientsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martinszuc.clientsapp.data.entity.ServiceType
import com.martinszuc.clientsapp.data.local.data_repository.ServiceTypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceTypeViewModel @Inject constructor(
    private val repository: ServiceTypeRepository
) : ViewModel() {
    private val _serviceTypes = MutableStateFlow<List<ServiceType>>(emptyList())
    val serviceTypes: StateFlow<List<ServiceType>> = _serviceTypes

    init {
        loadServiceTypes()
    }

    fun loadServiceTypes() {
        viewModelScope.launch {
            _serviceTypes.value = repository.getAllServiceTypes()
        }
    }

    fun addType(type: ServiceType) {
        viewModelScope.launch {
            repository.insertServiceType(type)
            loadServiceTypes()
        }
    }
}