package com.matos.app.ui.component.client

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.matos.app.data.entity.Client
import com.matos.app.ui.viewmodel.SharedClientViewModel

@Composable
fun ClientsScreen(viewModel: SharedClientViewModel = hiltViewModel()) {
    val clients by viewModel.clients.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Show add client dialog */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add Client")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (clients.isEmpty()) {
                Text(
                    text = "No clients found",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(clients) { client ->
                        ClientItem(client)
                    }
                }
            }
        }
    }
}

@Composable
fun ClientItem(client: Client) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = client.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = client.email ?: "No email", style = MaterialTheme.typography.bodyMedium)
            Text(text = client.phone ?: "No phone", style = MaterialTheme.typography.bodyMedium)
        }
    }
}