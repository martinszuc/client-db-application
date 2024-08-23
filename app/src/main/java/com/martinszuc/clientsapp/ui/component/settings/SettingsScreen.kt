package com.martinszuc.clientsapp.ui.component.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.ui.component.common.AppBarWithoutActions
import com.martinszuc.clientsapp.ui.component.common.dialogs.ConfirmationDialog
import com.martinszuc.clientsapp.ui.component.settings.dialogs.ThemeSelectionDialog
import com.martinszuc.clientsapp.utils.AppConstants
import kotlinx.coroutines.launch

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
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onThemeChanged: (String) -> Unit
) {
    val themePreference by viewModel.themePreference.collectAsState(initial = AppConstants.THEME_SYSTEM_DEFAULT)
    val scope = rememberCoroutineScope()

    var showThemeDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { AppBarWithoutActions(title = stringResource(R.string.settings)) }
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
                ConfirmationDialog(
                    title = stringResource(R.string.confirmation),
                    message = stringResource(R.string.delete_database_confirmation),
                    onConfirm = {
                        // Handle database deletion logic here
                        showDeleteDialog = false
                    },
                    onDismiss = { showDeleteDialog = false }
                )
            }

            // Theme selection dialog
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