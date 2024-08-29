package com.martinszuc.clientsapp.ui.component.service.service_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.ui.component.common.items.ServiceItem
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
fun ServiceListTab(
    navController: NavHostController,
    serviceViewModel: SharedServiceViewModel = hiltViewModel(),
    clientViewModel: SharedClientViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        serviceViewModel.loadServicesIfNotLoaded()
        clientViewModel.loadClients()
    }

    val services by serviceViewModel.services.collectAsState()
    val clients by clientViewModel.clients.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (services.isEmpty()) {
            Text(
                text = stringResource(R.string.no_services_found),
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(services) { service ->
                    val clientName = clients.find { it.id == service.client_id }?.name ?: "Unknown Client"
                    ServiceItem(
                        service = service,
                        clientName = clientName,
                        onClick = {
                            navController.navigate("serviceDetails/${service.id}")
                        }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_service))
        }

        if (showDialog) {
            AddServiceDialog(onDismissRequest = { showDialog = false })
        }
    }
}
