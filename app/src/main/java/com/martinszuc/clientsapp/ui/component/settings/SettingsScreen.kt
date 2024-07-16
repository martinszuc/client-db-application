package com.martinszuc.clientsapp.ui.component.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martinszuc.clientsapp.ui.component.settings.dialogs.ThemeSelectionDialog
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel(), onThemeChanged: (String) -> Unit) {
    val themePreference by viewModel.themePreference.collectAsState(initial = "system_default")
    val scope = rememberCoroutineScope()

    var showThemeDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ListItem(
            modifier = Modifier.clickable { showThemeDialog = true },
            headlineContent = { Text("Theme") },
            supportingContent = { Text(getThemeLabel(themePreference)) }
        )
        // Add more settings options here as needed

        if (showThemeDialog) {
            ThemeSelectionDialog(
                themePreference = themePreference,
                onDismissRequest = { showThemeDialog = false },
                onThemeSelected = { selectedTheme ->
                    scope.launch {
                        viewModel.changeTheme(selectedTheme)
                        onThemeChanged(selectedTheme)
                    }
                    showThemeDialog = false
                }
            )
        }
    }
}

@Composable
fun getThemeLabel(theme: String): String {
    return when (theme) {
        "light" -> "Light Theme"
        "dark" -> "Dark Theme"
        else -> "System Default"
    }
}