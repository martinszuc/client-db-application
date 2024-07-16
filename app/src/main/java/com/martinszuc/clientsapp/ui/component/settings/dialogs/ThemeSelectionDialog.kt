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
import androidx.compose.ui.unit.dp

@Composable
fun ThemeSelectionDialog(
    themePreference: String,
    onDismissRequest: () -> Unit,
    onThemeSelected: (String) -> Unit
) {
    val themes = listOf("system_default", "light", "dark")
    val themeLabels = mapOf(
        "system_default" to "System Default",
        "light" to "Light Theme",
        "dark" to "Dark Theme"
    )

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Select Theme") },
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
                Text("Cancel")
            }
        }
    )
}