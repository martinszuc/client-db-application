package com.martinszuc.clientsapp.ui.component.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.ui.AppBar
import com.martinszuc.clientsapp.ui.component.settings.dialogs.ThemeSelectionDialog
import com.martinszuc.clientsapp.util.AppConstants
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onThemeChanged: (String) -> Unit
) {
    val themePreference by viewModel.themePreference.collectAsState(initial = AppConstants.THEME_SYSTEM_DEFAULT)
    val scope = rememberCoroutineScope()

    var showThemeDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { AppBar(title = stringResource(R.string.settings)) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Theme Selection
            ListItem(
                modifier = Modifier.clickable { showThemeDialog = true },
                headlineContent = { Text(stringResource(R.string.theme)) },
                supportingContent = { Text(getThemeLabel(themePreference)) }
            )
            HorizontalDivider()

            // Database Management Section
            ListItem(
                modifier = Modifier.padding(top = 16.dp),
                headlineContent = {
                    Text(
                        text = stringResource(R.string.database),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
            ListItem(
                modifier = Modifier.clickable { showDeleteDialog = true },
                headlineContent = { Text(stringResource(R.string.delete_database)) })
            HorizontalDivider()
            ListItem(
                modifier = Modifier.clickable {
                    viewModel.backupDatabase()
                },
                headlineContent = { Text(stringResource(R.string.backup_database)) }
            )
            HorizontalDivider()

            // Confirmation dialog for deleting the database
            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text(text = stringResource(R.string.confirmation)) },
                    text = { Text(text = stringResource(R.string.delete_database_confirmation)) },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                // Handle database deletion logic here
                                showDeleteDialog = false
                            }
                        ) {
                            Text(text = stringResource(R.string.yes))
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDeleteDialog = false }
                        ) {
                            Text(text = stringResource(R.string.no))
                        }
                    }
                )
            }

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
}

@Composable
fun getThemeLabel(theme: String): String {
    return when (theme) {
        AppConstants.THEME_LIGHT -> stringResource(R.string.light_theme)
        AppConstants.THEME_DARK -> stringResource(R.string.dark_theme)
        else -> stringResource(R.string.system_default)
    }
}