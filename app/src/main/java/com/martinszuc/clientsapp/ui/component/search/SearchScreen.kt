package com.martinszuc.clientsapp.ui.component.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.martinszuc.clientsapp.data.entity.Client
import com.martinszuc.clientsapp.data.entity.Service
import com.martinszuc.clientsapp.ui.AppBar
import com.martinszuc.clientsapp.ui.component.client.ClientItem
import com.martinszuc.clientsapp.ui.component.service.ServiceItem
import com.martinszuc.clientsapp.ui.navigation.Screen
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel(),
    sharedClientViewModel: SharedClientViewModel = hiltViewModel()
) {
    val searchResults by viewModel.searchResults.collectAsState()
    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.search)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { newQuery ->
                    query = newQuery
                    viewModel.performSearch(newQuery)
                },
                label = { Text(stringResource(R.string.search)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            if (searchResults.isEmpty()) {
                Text("No results found", modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(searchResults, key = { item ->
                        when (item) {
                            is Client -> item.id
                            is Service -> item.id
                            else -> throw IllegalArgumentException("Unknown type")
                        }
                    }) { result ->
                        when (result) {
                            is Client -> ClientItem(client = result, onClick = { selectedClient ->
                                navController.navigate(Screen.ClientProfile(selectedClient.id).route)
                            })
                            is Service -> {
                                val clientName by produceState<String>("") {
                                    value = sharedClientViewModel.getClientName(result.client_id)
                                }
                                ServiceItem(service = result, clientName = clientName)
                            }
                        }
                    }
                }
            }
        }
    }
}