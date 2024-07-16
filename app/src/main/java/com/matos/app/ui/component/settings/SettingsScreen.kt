package com.matos.app.ui.component.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val themePreference by viewModel.themePreference.collectAsState(initial = "system_default")
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        OutlinedTextField(
            value = themePreference,
            onValueChange = { newTheme ->
                scope.launch {
                    viewModel.changeTheme(newTheme)
                }
            },
            label = { Text("Theme") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        ListItem(
            modifier = Modifier.clickable {
                scope.launch {
                    viewModel.changeTheme("light")
                }
            },
            headlineContent = { Text("Light Theme") }
        )
        ListItem(
            modifier = Modifier.clickable {
                scope.launch {
                    viewModel.changeTheme("dark")
                }
            },
            headlineContent = { Text("Dark Theme") }
        )
        ListItem(
            modifier = Modifier.clickable {
                scope.launch {
                    viewModel.changeTheme("system_default")
                }
            },
            headlineContent = { Text("System Default Theme") }
        )
    }
}