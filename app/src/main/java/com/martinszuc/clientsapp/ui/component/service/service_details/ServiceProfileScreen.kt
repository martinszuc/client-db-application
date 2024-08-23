package com.martinszuc.clientsapp.ui.component.service.service_details

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.martinszuc.clientsapp.ui.viewmodel.ServicePicturesViewModel
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel
import com.martinszuc.clientsapp.ui.viewmodel.SharedServiceViewModel

/**
 * Project: database application
 *
 * Author: Bc. Martin Szuc (matoszuc@gmail.com)
 * GitHub: https://github.com/martinszuc
 *
 *
 * License:
 * This code is licensed under MIT License. You may not use this file except
 * in compliance with the License.
 */

@Composable
fun ServiceProfileScreen(
    serviceId: Int,
    serviceViewModel: SharedServiceViewModel = hiltViewModel(),
    clientViewModel: SharedClientViewModel = hiltViewModel(),
    servicePicturesViewModel: ServicePicturesViewModel = hiltViewModel(),
    navController: NavHostController
) {
    // Collect the services state
    val services by serviceViewModel.services.collectAsState()
    val service = services.find { it.id == serviceId }
    var clientName by remember { mutableStateOf("Unknown Client") }
    val context = LocalContext.current  // Get context for showing Toast

    // Fetch the client name once and store it in local state
    LaunchedEffect(service?.client_id) {
        service?.let {
            clientName = clientViewModel.getClientName(it.client_id)
        }
    }

    // Load images for the service using ServicePicturesViewModel
    LaunchedEffect(serviceId) {
        servicePicturesViewModel.loadPhotosForService(serviceId)
    }

    // Fetch the photos and the loading state from the ServicePicturesViewModel
    val photos by servicePicturesViewModel.photos.collectAsState()
    val isLoadingPhotos by servicePicturesViewModel.isLoadingPhotos.collectAsState()

    // Callback to delete selected photos
    val onDeleteSelectedPhotos: (List<String>) -> Unit = { selectedUris ->
        val selectedPhotos = photos.filter { it.photoUri in selectedUris }
        servicePicturesViewModel.deleteSelectedPhotos(selectedPhotos) {
            // Reload photos after deletion
            servicePicturesViewModel.loadPhotosForService(serviceId)
        }
    }

    // Callback to delete the service
    val onDeleteService: () -> Unit = {
        service?.let {
            serviceViewModel.deleteService(it,
                onDeleteSuccess = {
                    // Show success toast
                    Toast.makeText(context, "Service deleted successfully", Toast.LENGTH_SHORT).show()
                    // Navigate back to the services screen
                    navController.navigate("services") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true  // Clear backstack
                        }
                    }
                },
                onDeleteFailure = { errorMessage ->
                    // Show error toast
                    Toast.makeText(context, "Failed to delete service: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    // Define the onAddPhotos callback to pass to the content
    val onAddPhotos: (List<Uri>) -> Unit = { uris ->
        service?.let {
            serviceViewModel.addPhotosForService(it.id, uris) { error ->
                Log.e("ServiceProfileScreen", "Failed to upload photos: $error")
            }
            // Reload the photos after adding
            servicePicturesViewModel.loadPhotosForService(serviceId)
        }
    }

    // Show the service content or a loading indicator if service is null
    if (service != null) {
        ServiceProfileContent(
            service = service,
            clientName = clientName,  // Use the locally stored client name
            imageUris = photos.map { it.photoUri },
            isLoadingImages = isLoadingPhotos,  // Pass loading state for images
            navController = navController,
            onAddPhotos = onAddPhotos,  // Pass the onAddPhotos callback
            onDeleteSelectedPhotos = onDeleteSelectedPhotos,  // Callback to delete selected photos
            onDeleteService = onDeleteService  // Pass the delete service callback
        )
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()  // Show loading indicator for service
            Log.d("ServiceProfileScreen", "Loading services or service not found")
        }
    }
}
