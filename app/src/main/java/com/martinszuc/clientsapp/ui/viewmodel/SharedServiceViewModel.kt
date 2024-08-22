package com.martinszuc.clientsapp.ui.viewmodel

import android.net.Uri
import com.martinszuc.clientsapp.data.entity.Service
import com.martinszuc.clientsapp.data.entity.ServicePhoto
import com.martinszuc.clientsapp.data.local.LocalStorageRepository
import com.martinszuc.clientsapp.data.remote.FirebaseStorageRepository
import com.martinszuc.clientsapp.data.repository.ServiceRepository
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
    fun loadServicesForClient(clientId: Int) {
        launchDataLoad(
            execution = {
                serviceRepository.getServicesForClient(clientId)
            },
            onSuccess = { servicesList ->
                _services.value = servicesList.sortedByDescending { it.id } // Sort by descending order
            },
            onFailure = {
                // Handle the failure
            }
        )
    }

    fun addServiceWithPhotos(service: Service, photoUris: List<Uri>, onUploadFailure: (String) -> Unit) {
        launchDataLoad(
            execution = {
                // Insert the service and get its ID
                val serviceId = serviceRepository.insertService(service)

                // Process each photo and ensure consistent naming
                photoUris.forEachIndexed { index, uri ->
                    val localDirectory = "service_images"
                    // Use the same consistent naming convention for local and Firebase storage
                    val fileName = "service_${serviceId}_photo_$index.jpg"

                    // Save photo to internal storage in the service_images directory
                    val photoFile = localStorageRepository.savePhotoToInternalStorage(uri, fileName, localDirectory)

                    if (photoFile != null) {
                        // Insert photo into the local database
                        val servicePhoto = ServicePhoto(
                            service_id = serviceId.toInt(),
                            photoUri = photoFile.absolutePath,  // Store local path
                            id = 0
                        )
                        serviceRepository.insertPhoto(servicePhoto)

                        // Try to upload the photo to Firebase
                        try {
                            val downloadUrl = firebaseStorageRepository.uploadPhotoWithDirectory(
                                Uri.fromFile(photoFile),
                                fileName,
                                localDirectory
                            )
                            if (downloadUrl == null) {
                                // Upload failed, notify the user
                                onUploadFailure("Failed to upload photo to Firebase: $fileName")
                            }
                        } catch (e: Exception) {
                            // Upload failed due to an exception
                            onUploadFailure("Upload error: ${e.localizedMessage}")
                        }
                    } else {
                        // Handle local file save failure
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



    // Add photos to an existing service based on its ID
    fun addPhotosForService(serviceId: Int, photoUris: List<Uri>) {
        launchDataLoad(
            execution = {
                // Insert each photo linked to the provided serviceId
                photoUris.forEach { uri ->
                    val servicePhoto = ServicePhoto(
                        service_id = serviceId,
                        photoUri = uri.toString(),
                        id = 0  // Room will auto-generate the ID
                    )
                    serviceRepository.insertPhoto(servicePhoto)
                }
            },
            onSuccess = {
                // Handle success (e.g., refresh UI)
            },
            onFailure = {
                // Handle failure
            }
        )
    }

    suspend fun getPhotosForService(serviceId: Int): List<ServicePhoto> {
        return serviceRepository.getPhotosForService(serviceId)
    }
}