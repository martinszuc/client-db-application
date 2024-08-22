package com.martinszuc.clientsapp.ui.component.client.profile.services_list

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
import androidx.navigation.NavHostController
import com.martinszuc.clientsapp.ui.navigation.Screen
import com.martinszuc.clientsapp.ui.viewmodel.ServiceCategoryViewModel
import com.martinszuc.clientsapp.ui.viewmodel.ServiceTypeViewModel
import com.martinszuc.clientsapp.ui.viewmodel.SharedServiceViewModel

@Composable
fun ProfileServicesTab(
    clientId: Int,
    navController: NavHostController,  // Accept NavHostController to navigate to service details
    sharedServiceViewModel: SharedServiceViewModel = hiltViewModel(),
    serviceCategoryViewModel: ServiceCategoryViewModel = hiltViewModel(),
    serviceTypeViewModel: ServiceTypeViewModel = hiltViewModel()
) {
    val categories by serviceCategoryViewModel.categories.collectAsState()
    val types by serviceTypeViewModel.serviceTypes.collectAsState()

    LaunchedEffect(clientId) {
        sharedServiceViewModel.loadServicesForClient(clientId)
        serviceCategoryViewModel.loadCategories()
        serviceTypeViewModel.loadServiceTypes()
    }

    val clientServices = sharedServiceViewModel.clientServices.collectAsState()
    val servicesForClient = clientServices.value[clientId] ?: emptyList()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(servicesForClient) { service ->
            val category = categories.find { it.id == service.category_id }
            val type = types.find { it.id == service.type_id }

            // Call ProfileServiceItem and pass the navigation to the service profile screen
            ProfileServiceItem(
                service = service,
                category = category,
                type = type,
                onClick = { serviceId ->
                    // Navigate to com.martinszuc.clientsapp.ui.component.service.service_details.ServiceProfileScreen when the item is clicked
                    navController.navigate(Screen.ServiceProfile(serviceId).route)
                }
            )
        }
    }
}
