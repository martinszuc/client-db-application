package com.martinszuc.clientsapp.ui.component.service.service_list

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.data.entity.Service
import com.martinszuc.clientsapp.ui.component.service.ServiceDropdownMenu
import com.martinszuc.clientsapp.ui.viewmodel.ServiceCategoryViewModel
import com.martinszuc.clientsapp.ui.viewmodel.ServiceTypeViewModel
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel
import com.martinszuc.clientsapp.ui.viewmodel.SharedServiceViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddServiceDialog(
    onDismissRequest: () -> Unit,
    sharedServiceViewModel: SharedServiceViewModel = hiltViewModel(),
    sharedClientViewModel: SharedClientViewModel = hiltViewModel(),
    serviceCategoryViewModel: ServiceCategoryViewModel = hiltViewModel(),
    serviceTypeViewModel: ServiceTypeViewModel = hiltViewModel()
) {
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var selectedClientId by remember { mutableStateOf<Int?>(null) }
    var selectedDate by remember { mutableStateOf(Date()) }
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    var selectedTypeId by remember { mutableStateOf<Int?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val clients by sharedClientViewModel.clients.collectAsState()
    val categories by serviceCategoryViewModel.categories.collectAsState()
    val types by serviceTypeViewModel.serviceTypes.collectAsState()

    LaunchedEffect(Unit) {
        sharedClientViewModel.loadClients()
        serviceCategoryViewModel.loadCategories()
        serviceTypeViewModel.loadServiceTypes()
    }

    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
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
                Spacer(modifier = Modifier.height(8.dp))
                ServiceTypeDropdownMenu(
                    categories = categories,
                    types = types,
                    selectedCategoryId = selectedCategoryId,
                    selectedTypeId = selectedTypeId,
                    onCategorySelected = { categoryId -> selectedCategoryId = categoryId },
                    onTypeSelected = { typeId -> selectedTypeId = typeId }
                )
            }
        },
        confirmButton = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.cancel), color = MaterialTheme.colorScheme.onPrimary)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        val servicePrice = price.toDoubleOrNull()
                        if (selectedClientId == null || description.isEmpty() || servicePrice == null || selectedCategoryId == null || selectedTypeId == null) {
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
                            category_id = selectedCategoryId,
                            type_id = selectedTypeId
                        )
                        scope.launch {
                            sharedServiceViewModel.addService(service)
                            sharedClientViewModel.updateLatestServiceDate(selectedClientId!!, selectedDate)
                            sharedClientViewModel.loadClients()
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
