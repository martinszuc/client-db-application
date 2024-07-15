package com.matos.app.ui.component.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.matos.app.data.entity.Client
import com.matos.app.data.entity.Service
import com.matos.app.ui.component.client.ClientItem
import com.matos.app.ui.component.service.ServiceItem

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchResults by viewModel.searchResults.collectAsState()
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = { newQuery ->
                query = newQuery
                viewModel.performSearch(newQuery)
            },
            label = { Text("Search") },
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
                        is Client -> ClientItem(client = result)
                        is Service -> ServiceItem(service = result)
                    }
                }
            }
        }
    }
}
