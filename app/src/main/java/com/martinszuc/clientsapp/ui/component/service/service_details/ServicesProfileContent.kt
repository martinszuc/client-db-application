package com.martinszuc.clientsapp.ui.component.service.service_details

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.data.entity.Service
import com.martinszuc.clientsapp.ui.component.common.AppBarWithOptionalBackButton
import com.martinszuc.clientsapp.ui.component.common.dialogs.ConfirmationDialog
import com.martinszuc.clientsapp.utils.DateUtils
import com.martinszuc.clientsapp.utils.imageViewer.FullScreenImageViewer

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
fun ServiceProfileContent(
    service: Service,
    clientName: String,
    imageUris: List<String>,
    isLoadingImages: Boolean,
    navController: NavHostController,
    onAddPhotos: (List<Uri>) -> Unit,
    onDeleteSelectedPhotos: (List<String>) -> Unit,  // Callback for deleting selected photos
    onDeleteService: () -> Unit  // Callback for deleting the entire service
) {
    var selectedImageUri by remember { mutableStateOf<String?>(null) }
    var showMenu by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showDeleteServiceDialog by remember { mutableStateOf(false) }
    val selectedPhotos = remember { mutableStateListOf<String>() }  // List of selected photos

    // File picker launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            onAddPhotos(uris)  // Pass selected photos to the parent handler
        }
    }

    Scaffold(
        topBar = {
            AppBarWithOptionalBackButton(
                title = if (selectedPhotos.isEmpty()) stringResource(R.string.service_details)
                else stringResource(R.string.delete_selected_photos),
                onBackClick = { navController.popBackStack() },
                actions = {
                    if (selectedPhotos.isNotEmpty()) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete_selected_photos)
                            )
                        }
                    } else {
                        IconButton(onClick = { filePickerLauncher.launch("image/*") }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.add_service)
                            )
                        }
                    }

                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more_options)
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                showDeleteServiceDialog = true
                                showMenu = false
                            },
                            text = { Text(text = stringResource(R.string.delete_service)) }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = service.description,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Client Name
            Text(
                text = clientName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Price
            Text(
                text = stringResource(R.string.cena, service.price.toString()),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Date
            val formattedDate = DateUtils.formatLongDateWithTime(service.date)
            Text(
                text = stringResource(R.string.d_tum, formattedDate),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Loading Indicator for Images
            if (isLoadingImages) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = stringResource(R.string.loading_images),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            // Display Images in a 2x2 grid with a fixed height
            if (!isLoadingImages && imageUris.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.images),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Grid of Images with selection feature (constrained height)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)  // Constrain height to avoid infinite height error
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(imageUris) { uri ->
                            val isSelected = selectedPhotos.contains(uri)
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .border(2.dp, if (isSelected) Color.Red else Color.Transparent)
                                    .clickable {
                                        if (isSelected) {
                                            selectedPhotos.remove(uri)
                                        } else {
                                            selectedPhotos.add(uri)
                                        }
                                    }
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(uri),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Full-Screen Image Viewer
            selectedImageUri?.let { uri ->
                FullScreenImageViewer(
                    imageUri = uri,
                    onDismiss = { selectedImageUri = null }
                )
            }

            // Confirmation Dialog for deleting selected photos
            if (showDeleteDialog) {
                ConfirmationDialog(
                    title = stringResource(R.string.confirmation),
                    message = stringResource(R.string.delete_selected_photos_confirmation),
                    onConfirm = {
                        onDeleteSelectedPhotos(selectedPhotos)
                        selectedPhotos.clear()  // Clear selection after deletion
                        showDeleteDialog = false
                    },
                    onDismiss = { showDeleteDialog = false }
                )
            }

            // Confirmation Dialog for deleting the entire service
            if (showDeleteServiceDialog) {
                ConfirmationDialog(
                    title = stringResource(R.string.confirmation),
                    message = stringResource(R.string.delete_service_confirmation),
                    onConfirm = {
                        onDeleteService()
                        showDeleteServiceDialog = false
                    },
                    onDismiss = { showDeleteServiceDialog = false }
                )
            }
        }
    }
}
