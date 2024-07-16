package com.martinszuc.clientsapp.ui.component.service

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martinszuc.clientsapp.data.entity.Service
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel
import com.martinszuc.clientsapp.ui.viewmodel.SharedServiceViewModel
import kotlinx.coroutines.launch
import java.util.Date

@Composable
fun AddServiceDialog(
    onDismissRequest: () -> Unit,
    sharedServiceViewModel: SharedServiceViewModel = hiltViewModel(),
    sharedClientViewModel: SharedClientViewModel = hiltViewModel()
) {
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var selectedClientIndex by remember { mutableStateOf(-1) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val clients by sharedClientViewModel.clients.collectAsState()

    LaunchedEffect(Unit) {
        sharedClientViewModel.loadClients()
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Add Service") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (clients.isNotEmpty()) {
                    val clientNames = clients.map { it.name }
                    DropdownMenu(
                        items = clientNames,
                        selectedIndex = selectedClientIndex,
                        onItemSelected = { index ->
                            selectedClientIndex = index
                        }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        val servicePrice = price.toDoubleOrNull()
                        if (selectedClientIndex == -1 || description.isEmpty() || servicePrice == null) {
                            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val clientId = clients[selectedClientIndex].id
                        val service = Service(0, clientId, description, Date(), servicePrice)
                        scope.launch {
                            sharedServiceViewModel.addService(service)
                            Toast.makeText(context, "Service added successfully", Toast.LENGTH_SHORT).show()
                            onDismissRequest()
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Text("Save")
                }
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun DropdownMenu(
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedItem = if (selectedIndex >= 0) items[selectedIndex] else "Select Client"
    Box(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Text(text = selectedItem)
        }
        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        onItemSelected(index)
                        expanded = false
                    }
                )
            }
        }
    }
}
