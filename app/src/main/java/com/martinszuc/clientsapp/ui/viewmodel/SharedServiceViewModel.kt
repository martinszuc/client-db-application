package com.martinszuc.clientsapp.ui.viewmodel

import android.net.Uri
import com.martinszuc.clientsapp.data.entity.Service
import com.martinszuc.clientsapp.data.entity.ServicePhoto
import com.martinszuc.clientsapp.data.local.LocalStorageRepository
import com.martinszuc.clientsapp.data.remote.FirebaseStorageRepository
import com.martinszuc.clientsapp.data.local.data_repository.ServiceRepository
import com.martinszuc.clientsapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedServiceViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
    private val localStorageRepository: LocalStorageRepository,
    private val firebaseStorageRepository: FirebaseStorageRepository
) : BaseViewModel() {

    private val _services = MutableStateFlow<List<Service>>(emptyList())
    val services: StateFlow<List<Service>> = _services.asStateFlow()

    private val _clientServices = MutableStateFlow<Map<Int, List<Service>>>(emptyMap())
    val clientServices: StateFlow<Map<Int, List<Service>>> = _clientServices.asStateFlow()

    // Flag to check if services are already loaded
    private var areServicesLoaded = false

    init {
        loadServicesIfNotLoaded()  // Automatically load services on init
    }

    // Load all services if they are not already loaded
    fun loadServicesIfNotLoaded() {
        if (!areServicesLoaded) {
            loadServices()
        }
    }

    // Load services from the repository
    private fun loadServices() {
        launchDataLoad(
            execution = {
                serviceRepository.getServices()
            },
            onSuccess = { servicesList ->
                _services.value = servicesList.sortedByDescending { it.id }
                areServicesLoaded = true  // Mark as loaded
            },
            onFailure = {
                // Handle failure (log, show an error, etc.)
            }
        )
    }

    fun loadServicesForClient(clientId: Int) {
        val clientServices = _services.value.filter { it.client_id == clientId }
        _clientServices.value = _clientServices.value.toMutableMap().apply {
            put(clientId, clientServices)
        }
    }

    private fun updateClientServices(servicesList: List<Service>) {
        val groupedServices = servicesList.groupBy { it.client_id }
        _clientServices.value = groupedServices
    }

    // Add a new service and update both the all-services list and client-specific map
    fun addService(service: Service) {
        launchDataLoad(
            execution = {
                serviceRepository.insertService(service)
                serviceRepository.getServices() // Ensure this returns the updated list
            },
            onSuccess = { servicesList ->
                _services.value = servicesList.sortedByDescending { it.id }
                updateClientServices(servicesList)
            },
            onFailure = {
                // Handle the failure
            }
        )
    }

    // Add a new service with photos
    fun addServiceWithPhotos(service: Service, photoUris: List<Uri>, onUploadFailure: (String) -> Unit) {
        launchDataLoad(
            execution = {
                // Insert the service and get its ID
                val serviceId = serviceRepository.insertService(service)

                // Process each photo and ensure consistent naming
                photoUris.forEachIndexed { index, uri ->
                    val localDirectory = "service_images"
                    val fileName = "service_${serviceId}_photo_$index.jpg"

                    val photoFile = localStorageRepository.savePhotoToInternalStorage(uri, fileName, localDirectory)

                    if (photoFile != null) {
                        // Insert photo into the local database
                        val servicePhoto = ServicePhoto(
                            service_id = serviceId.toInt(),
                            photoUri = photoFile.absolutePath,  // Store local path
                            id = 0
                        )
                        serviceRepository.insertPhoto(servicePhoto)

                        // Upload to Firebase
                        try {
                            val downloadUrl = firebaseStorageRepository.uploadPhotoWithDirectory(
                                Uri.fromFile(photoFile),
                                fileName,
                                localDirectory
                            )
                            if (downloadUrl == null) {
                                onUploadFailure("Failed to upload photo to Firebase: $fileName")
                            }
                        } catch (e: Exception) {
                            onUploadFailure("Upload error: ${e.localizedMessage}")
                        }
                    } else {
                        onUploadFailure("Failed to save photo to internal storage: $fileName")
                    }
                }
            },
            onSuccess = {
                // Handle success (e.g., update UI)
            },
            onFailure = {
                // Handle failure (e.g., show error)
            }
        )
    }

    fun addPhotosForService(serviceId: Int, photoUris: List<Uri>, onUploadFailure: (String) -> Unit) {
        launchDataLoad(
            execution = {
                photoUris.forEachIndexed { index, uri ->
                    val localDirectory = "service_images"
                    val fileName = "service_${serviceId}_photo_$index.jpg"

                    val photoFile = localStorageRepository.savePhotoToInternalStorage(uri, fileName, localDirectory)

                    if (photoFile != null) {
                        val servicePhoto = ServicePhoto(
                            service_id = serviceId,
                            photoUri = photoFile.absolutePath,
                            id = 0
                        )
                        serviceRepository.insertPhoto(servicePhoto)

                        // Upload to Firebase
                        val downloadUrl = firebaseStorageRepository.uploadPhotoWithDirectory(
                            Uri.fromFile(photoFile),
                            fileName,
                            localDirectory
                        )

                        if (downloadUrl == null) {
                            onUploadFailure("Failed to upload photo: $fileName")
                        }
                    } else {
                        onUploadFailure("Failed to save photo to internal storage: $fileName")
                    }
                }
            },
            onSuccess = {
                // Handle success (refresh UI or similar actions)
            },
            onFailure = {
                // Handle any errors
            }
        )
    }

    // Delete a service and its associated photos
    fun deleteService(service: Service, onDeleteSuccess: () -> Unit, onDeleteFailure: (String) -> Unit) {
        launchDataLoad(
            execution = {
                // First, delete all photos associated with the service
                serviceRepository.deletePhotosForService(service.id)

                // Then, delete the service itself
                serviceRepository.deleteService(service)
            },
            onSuccess = {
                // Successfully deleted service and photos
                onDeleteSuccess()
            },
            onFailure = {
                // Handle failure
                onDeleteFailure("Failed to delete service: ${it.localizedMessage}")
            }
        )
    }
}