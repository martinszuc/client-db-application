package com.martinszuc.clientsapp.ui.component.service

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.data.entity.Client
import com.martinszuc.clientsapp.ui.component.common.ProfilePicture
import com.martinszuc.clientsapp.utils.getInitials

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
fun ServiceDropdownMenu(
    clients: List<Client>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedClient = if (selectedIndex >= 0) clients[selectedIndex] else null
    val selectedItem = selectedClient?.name ?: stringResource(R.string.select_client_add)

    // Use the selected client's profile picture color, or default to MaterialTheme's primary color
    val buttonColor = selectedClient?.profilePictureColor?.let { colorHex ->
        try {
            androidx.compose.ui.graphics.Color(android.graphics.Color.parseColor(colorHex))
        } catch (e: Exception) {
            MaterialTheme.colorScheme.primary // Fallback if the color string is invalid
        }
    } ?: MaterialTheme.colorScheme.primary

    Box(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = selectedItem, color = MaterialTheme.colorScheme.onPrimary)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            clients.forEachIndexed { index, client ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            ProfilePicture(
                                profilePictureUrl = client.profilePictureUrl,
                                initials = getInitials(client.name),
                                profilePictureColor = client.profilePictureColor,
                                size = 40
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = client.name, color = MaterialTheme.colorScheme.onSurface)
                        }
                    },
                    onClick = {
                        onItemSelected(index)
                        expanded = false
                    },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }
}
