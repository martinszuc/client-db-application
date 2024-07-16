package com.martinszuc.clientsapp.ui.component.client

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.ui.AppBar
import com.martinszuc.clientsapp.ui.component.client.add_client.AddClientDialog
import com.martinszuc.clientsapp.ui.navigation.Screen
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel

@Composable
fun ClientsScreen(
    navController: NavHostController,
    sharedClientViewModel: SharedClientViewModel = hiltViewModel()
) {
    val logTag = "ClientsScreen"
    val clients by sharedClientViewModel.clients.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        Log.d(logTag, "Launched effect triggered, loading clients")
        sharedClientViewModel.loadClients()
    }

    Scaffold(
        topBar = { AppBar(title = stringResource(R.string.label_clients)) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Log.d(logTag, "Add Client button clicked")
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Client")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (clients.isEmpty()) {
                Log.d(logTag, "No clients found")
                Text(
                    text = "No clients found",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Log.d(logTag, "Clients found: ${clients.size}")
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(clients) { client ->
                        ClientItem(client = client, onClick = { selectedClient ->
                            Log.d(logTag, "Client item clicked: ${selectedClient.id}")
                            navController.navigate(Screen.ClientProfile(selectedClient.id).route)
                        })
                    }
                }
            }
        }

        if (showDialog) {
            AddClientDialog(onDismissRequest = {
                Log.d(logTag, "Add Client dialog dismissed")
                showDialog = false
            })
        }
    }
}