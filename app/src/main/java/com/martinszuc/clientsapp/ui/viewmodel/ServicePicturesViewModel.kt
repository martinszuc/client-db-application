package com.martinszuc.clientsapp.ui.viewmodel

import com.martinszuc.clientsapp.data.entity.ServicePhoto
import com.martinszuc.clientsapp.data.repository.ServiceRepository
import com.martinszuc.clientsapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ServicePicturesViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository
) : BaseViewModel() {

    private val _photos = MutableStateFlow<List<ServicePhoto>>(emptyList())
    val photos: StateFlow<List<ServicePhoto>> = _photos.asStateFlow()

    // Flag to track if images are loading
    private val _isLoadingPhotos = MutableStateFlow(false)
    val isLoadingPhotos: StateFlow<Boolean> = _isLoadingPhotos.asStateFlow()

    // Fetch photos for a specific service
    fun loadPhotosForService(serviceId: Int) {
        _isLoadingPhotos.value = true
        launchDataLoad(
            execution = {
                serviceRepository.getPhotosForService(serviceId)
            },
            onSuccess = { photosList ->
                _photos.value = photosList
                _isLoadingPhotos.value = false  // Mark loading as finished
            },
            onFailure = {
                _isLoadingPhotos.value = false  // Handle failure and stop loading
            }
        )
    }
}