package com.martinszuc.clientsapp.ui.component.service.service_details

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.data.entity.Service
import com.martinszuc.clientsapp.util.imageViewer.FullScreenImageViewer
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
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
            TopAppBar(
                title = {
                    Text(
                        text = if (selectedPhotos.isEmpty()) stringResource(R.string.service_details)
                        else stringResource(R.string.delete_selected_photos)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
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

                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary)
                    ) {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = stringResource(R.string.more_options),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        // Scrollable content without verticalScroll() on the parent Column
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
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val formattedDate = dateFormat.format(service.date)
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
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text(text = stringResource(R.string.confirmation)) },
                    text = { Text(text = stringResource(R.string.delete_selected_photos_confirmation)) },
                    confirmButton = {
                        TextButton(onClick = {
                            onDeleteSelectedPhotos(selectedPhotos)
                            selectedPhotos.clear()  // Clear selection after deletion
                            showDeleteDialog = false
                        }) {
                            Text(text = stringResource(R.string.yes))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text(text = stringResource(R.string.no))
                        }
                    }
                )
            }

            // Confirmation Dialog for deleting the entire service
            if (showDeleteServiceDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteServiceDialog = false },
                    title = { Text(text = stringResource(R.string.confirmation)) },
                    text = { Text(text = stringResource(R.string.delete_service_confirmation)) },
                    confirmButton = {
                        TextButton(onClick = {
                            onDeleteService()
                            showDeleteServiceDialog = false
                        }) {
                            Text(text = stringResource(R.string.yes))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteServiceDialog = false }) {
                            Text(text = stringResource(R.string.no))
                        }
                    }
                )
            }
        }
    }
}
