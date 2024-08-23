package com.martinszuc.clientsapp.ui.component.settings.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.utils.AppConstants.THEME_DARK
import com.martinszuc.clientsapp.utils.AppConstants.THEME_LIGHT
import com.martinszuc.clientsapp.utils.AppConstants.THEME_SYSTEM_DEFAULT

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
fun ThemeSelectionDialog(
    themePreference: String,
    onDismissRequest: () -> Unit,
    onThemeSelected: (String) -> Unit
) {
    val themes = listOf(THEME_SYSTEM_DEFAULT, THEME_LIGHT, THEME_DARK)
    val themeLabels = mapOf(
        THEME_SYSTEM_DEFAULT to stringResource(R.string.system_default),
        THEME_LIGHT to stringResource(R.string.light_theme),
        THEME_DARK to stringResource(R.string.dark_theme)
    )

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.select_theme)) },
        text = {
            Column {
                themes.forEach { theme ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onThemeSelected(theme) }
                            .padding(16.dp)
                    ) {
                        RadioButton(
                            selected = theme == themePreference,
                            onClick = { onThemeSelected(theme) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = themeLabels[theme] ?: theme)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}