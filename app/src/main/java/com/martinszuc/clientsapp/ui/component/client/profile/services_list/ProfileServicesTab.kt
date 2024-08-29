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
import com.martinszuc.clientsapp.ui.component.common.items.ProfileServiceItem
import com.martinszuc.clientsapp.ui.navigation.Screen
import com.martinszuc.clientsapp.ui.viewmodel.ProfileServiceViewModel
import com.martinszuc.clientsapp.ui.viewmodel.ServiceCategoryViewModel
import com.martinszuc.clientsapp.ui.viewmodel.ServiceTypeViewModel
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
fun ProfileServicesTab(
    clientId: Int,
    navController: NavHostController,
    profileServiceViewModel: ProfileServiceViewModel = hiltViewModel(),
) {
    // Fetch services for the given client when the tab is opened
    LaunchedEffect(clientId) {
        profileServiceViewModel.loadServicesForClient(clientId)
    }

    // Collect services for the client
    val clientServices by profileServiceViewModel.clientServices.collectAsState()

    // Display services using LazyColumn
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(clientServices) { service ->
            ProfileServiceItem(
                service = service,
                onClick = { serviceId ->
                    navController.navigate(Screen.ServiceProfile(serviceId).route)
                }
            )
        }
    }
}
