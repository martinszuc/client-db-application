package com.martinszuc.clientsapp.ui.component.client.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.martinszuc.clientsapp.ui.component.service.ServiceItem
import com.martinszuc.clientsapp.ui.viewmodel.SharedServiceViewModel

@Composable
fun ProfileServicesTab(clientId: Int, sharedServiceViewModel: SharedServiceViewModel) {
    val services by sharedServiceViewModel.services.collectAsState()

    LaunchedEffect(clientId) {
        sharedServiceViewModel.loadServicesForClient(clientId)
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(services) { service ->
            ServiceItem(service = service, clientName = "Client Name") // Replace with actual client name if needed
        }
    }
}