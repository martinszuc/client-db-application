package com.martinszuc.clientsapp.ui.component.service.service_list

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.data.entity.Service
import com.martinszuc.clientsapp.ui.component.service.ServiceDropdownMenu
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel
import com.martinszuc.clientsapp.ui.viewmodel.SharedServiceViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AddServiceDialog(
    onDismissRequest: () -> Unit,
    sharedServiceViewModel: SharedServiceViewModel = hiltViewModel(),
    sharedClientViewModel: SharedClientViewModel = hiltViewModel()
) {
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var selectedClientId by remember { mutableStateOf<Int?>(null) }
    var selectedDate by remember { mutableStateOf(Date()) }
    var photoUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val clients by sharedClientViewModel.clients.collectAsState()

    // Load clients
    LaunchedEffect(Unit) {
        sharedClientViewModel.loadClients()
    }

    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            selectedDate = calendar.time
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    // File picker launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri> ->
            if (uris.size <= 4) {  // Limit to 4 photos
                photoUris = uris
            } else {
                Toast.makeText(context, "You can only select up to 4 photos", Toast.LENGTH_SHORT).show()
            }
        }
    )

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.add_service)) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (clients.isNotEmpty()) {
                    ServiceDropdownMenu(
                        clients = clients,
                        selectedIndex = clients.indexOfFirst { it.id == selectedClientId },
                        onItemSelected = { index ->
                            selectedClientId = clients.getOrNull(index)?.id
                        }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = stringResource(R.string.description)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text(text = stringResource(R.string.price)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        IconButton(
                            onClick = { datePickerDialog.show() },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = stringResource(R.string.select_date),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Text(
                            text = dateFormat.format(selectedDate),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        IconButton(
                            onClick = { timePickerDialog.show() },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_clock),
                                contentDescription = stringResource(R.string.select_time),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Text(
                            text = timeFormat.format(selectedDate),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Button to pick photos
                Button(
                    onClick = { filePickerLauncher.launch("image/*") },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Pick Photos")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Display selected photos (up to 4)
                if (photoUris.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(photoUris) { uri ->
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = null,
                                modifier = Modifier.size(64.dp)  // Smaller size for photos
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.cancel), color = MaterialTheme.colorScheme.onPrimary)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        val servicePrice = price.toDoubleOrNull()
                        if (selectedClientId == null || description.isEmpty() || servicePrice == null) {
                            Toast.makeText(context,
                                context.getString(R.string.please_fill_in_all_fields), Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val service = Service(
                            id = 0,
                            client_id = selectedClientId!!,
                            description = description,
                            date = selectedDate,
                            price = servicePrice,
                            category_id = null,
                            type_id = null
                        )
                        scope.launch {
                            sharedServiceViewModel.addServiceWithPhotos(service, photoUris) { message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                            Toast.makeText(context, context.getString(R.string.service_added_successfully), Toast.LENGTH_SHORT).show()
                            onDismissRequest()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.save), color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    )
}
