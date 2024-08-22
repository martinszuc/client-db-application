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
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.ui.component.service.ServiceItem
import com.martinszuc.clientsapp.ui.viewmodel.ServiceCategoryViewModel
import com.martinszuc.clientsapp.ui.viewmodel.ServiceTypeViewModel
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel
import com.martinszuc.clientsapp.ui.viewmodel.SharedServiceViewModel

@Composable
fun ServiceListTab(
    navController: NavHostController,  // Accept NavController
    serviceViewModel: SharedServiceViewModel = hiltViewModel(),
    clientViewModel: SharedClientViewModel = hiltViewModel(),
    categoryViewModel: ServiceCategoryViewModel = hiltViewModel(),
    typeViewModel: ServiceTypeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        serviceViewModel.loadServicesIfNotLoaded()
        clientViewModel.loadClients()
        categoryViewModel.loadCategories()
        typeViewModel.loadServiceTypes()
    }

    val services by serviceViewModel.services.collectAsState()
    val clients by clientViewModel.clients.collectAsState()
    val categories by categoryViewModel.categories.collectAsState()
    val types by typeViewModel.serviceTypes.collectAsState()
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
                    val clientName by produceState(initialValue = "") {
                        value = clients.find { it.id == service.client_id }?.name ?: "Unknown Client"
                    }
                    val category = categories.find { it.id == service.category_id }
                    val type = types.find { it.id == service.type_id }

                    ServiceItem(
                        service = service,
                        clientName = clientName,
                        category = category,
                        type = type,
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
