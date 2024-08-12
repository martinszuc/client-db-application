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
import androidx.hilt.navigation.compose.hiltViewModel
import com.martinszuc.clientsapp.ui.viewmodel.ServiceCategoryViewModel
import com.martinszuc.clientsapp.ui.viewmodel.ServiceTypeViewModel
import com.martinszuc.clientsapp.ui.viewmodel.SharedServiceViewModel

@Composable
fun ProfileServicesTab(
    clientId: Int,
    sharedServiceViewModel: SharedServiceViewModel = hiltViewModel(),
    serviceCategoryViewModel: ServiceCategoryViewModel = hiltViewModel(),
    serviceTypeViewModel: ServiceTypeViewModel = hiltViewModel()
) {
    val services by sharedServiceViewModel.services.collectAsState()
    val categories by serviceCategoryViewModel.categories.collectAsState()
    val types by serviceTypeViewModel.serviceTypes.collectAsState()

    LaunchedEffect(clientId) {
        sharedServiceViewModel.loadServicesForClient(clientId)
        serviceCategoryViewModel.loadCategories()
        serviceTypeViewModel.loadServiceTypes()
    }

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        items(services) { service ->
            val category = categories.find { it.id == service.category_id }
            val type = types.find { it.id == service.type_id }
            ProfileServiceItem(service = service, category = category, type = type)
        }
    }
}