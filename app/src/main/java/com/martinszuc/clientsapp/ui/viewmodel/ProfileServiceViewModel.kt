package com.martinszuc.clientsapp.ui.viewmodel

/**
 * Project: Clients database application
 * File: ProfileServiceViewModel
 *
 * Author: Bc. Martin Szuc (matoszuc@gmail.com)
 * GitHub: https://github.com/martinszuc
 *
 * Created on: 8/25/24 at 1:41 PM
 *
 *
 * License:
 * This code is licensed under MIT License. You may not use this file except
 * in compliance with the License.
 */

import android.util.Log
import com.martinszuc.clientsapp.data.entity.Service
import com.martinszuc.clientsapp.data.local.data_repository.ServiceRepository
import com.martinszuc.clientsapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileServiceViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository
) : BaseViewModel() {
    private val logTag = "ProfileServiceViewModel"
    private val _clientServices = MutableStateFlow<List<Service>>(emptyList())
    val clientServices: StateFlow<List<Service>> = _clientServices

    fun loadServicesForClient(clientId: Int) {
        launchDataLoad(
            execution = {
                serviceRepository.getServicesForClient(clientId)
            },
            onSuccess = { services ->
                _clientServices.value = services  // Update the state with the services
            },
            onFailure = {
                // Handle failure, log, or show error message if needed
                Log.e(logTag, "Failed to load services for client $clientId: ${it.message}")
            }
        )
    }
}
