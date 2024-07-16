package com.martinszuc.clientsapp.ui.component.client.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.ui.component.profile.ProfilePicture
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientProfileScreen(
    clientId: Int,
    navController: NavHostController,
    sharedClientViewModel: SharedClientViewModel = hiltViewModel()
) {
    val client by sharedClientViewModel.selectedClient.collectAsState()

    LaunchedEffect(clientId) {
        sharedClientViewModel.getClientById(clientId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.client_profile)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        client?.let { client ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfilePicture(
                    profilePictureUrl = client.profilePictureUrl,
                    initials = client.name.take(2),
                    profilePictureColor = client.profilePictureColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = client.name, style = MaterialTheme.typography.titleLarge)
                Text(text = client.email ?: "No email", style = MaterialTheme.typography.bodyMedium)
                Text(text = client.phone ?: "No phone", style = MaterialTheme.typography.bodyMedium)
                // Add more client details here
            }
        } ?: run {
            Text(text = "Client not found", style = MaterialTheme.typography.bodyMedium)
        }
    }
}