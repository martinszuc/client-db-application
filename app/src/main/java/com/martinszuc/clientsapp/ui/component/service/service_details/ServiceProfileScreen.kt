package com.martinszuc.clientsapp.ui.component.service.service_details

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.martinszuc.clientsapp.ui.viewmodel.ServicePicturesViewModel
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel
import com.martinszuc.clientsapp.ui.viewmodel.SharedServiceViewModel

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

    // Find the service by ID
    val service = services.find { it.id == serviceId }

    // Log the service state for debugging
    if (service != null) {
        Log.d("ServiceProfileScreen", "Fetched service: ${service.description}")
    } else if (services.isNotEmpty()) {
        Log.d("ServiceProfileScreen", "Service with ID: $serviceId not found")
    } else {
        Log.d("ServiceProfileScreen", "Services are still loading")
    }

    // Load images for the service using ServicePicturesViewModel
    LaunchedEffect(serviceId) {
        servicePicturesViewModel.loadPhotosForService(serviceId)
    }

    // Fetch the photos and the loading state from the ServicePicturesViewModel
    val photos by servicePicturesViewModel.photos.collectAsState()
    val isLoadingPhotos by servicePicturesViewModel.isLoadingPhotos.collectAsState()

    // State to store client name locally
    var clientName by remember { mutableStateOf("Unknown Client") }

    // Fetch the client name once and store it in local state
    LaunchedEffect(service?.client_id) {
        service?.let {
            val fetchedClientName = clientViewModel.getClientName(it.client_id)
            Log.d("ServiceProfileScreen", "Fetched client name: $fetchedClientName for client ID: ${it.client_id}")
            clientName = fetchedClientName
        }
    }

    // Define the onAddPhotos callback to pass to the content
    val onAddPhotos: (List<Uri>) -> Unit = { uris ->
        service?.let {
            serviceViewModel.addPhotosForService(it.id, uris) { error ->
                Log.e("ServiceProfileScreen", "Failed to upload photos: $error")
            }
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
            onAddPhotos = onAddPhotos  // Pass the onAddPhotos callback
        )
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()  // Show loading indicator for service
            Log.d("ServiceProfileScreen", "Loading services or service not found")
        }
    }
}
