package com.martinszuc.clientsapp.ui.component.client.add_client

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.data.entity.Client
import com.martinszuc.clientsapp.ui.component.common.CommonOutlinedTextField
import com.martinszuc.clientsapp.ui.component.common.OkAndCancelButtons
import com.martinszuc.clientsapp.ui.component.common.ProfilePicture
import com.martinszuc.clientsapp.ui.theme.profilePictureColors
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel
import com.martinszuc.clientsapp.utils.getContactInfo
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
fun AddClientDialog(
    onDismissRequest: () -> Unit,
    sharedClientViewModel: SharedClientViewModel = hiltViewModel(),
    context: Context
) {
    val logTag = "AddClientDialog"

    // Initialize the state for the form fields
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var profilePictureUrl by remember { mutableStateOf<String?>(null) }

    // Initialize the profile picture color with a random color from the list
    var profilePictureColor by remember { mutableStateOf<String?>(profilePictureColors.random()) }
    var showColorDialog by remember { mutableStateOf(false) }

    // Contact picker launcher
    val contactPickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickContact()) { uri ->
            uri?.let {
                val contactInfo = getContactInfo(context, it)
                name = contactInfo.name
                phone = contactInfo.phone
                email = contactInfo.email
            }
        }

    // Image picker launcher
    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                Log.d(logTag, "Image selected: $it")
                profilePictureUrl = it.toString()
                profilePictureColor = null // Clear the color if an image is uploaded
            }
        }

    // Color picker dialog
    if (showColorDialog) {
        ColorPickerDialog(
            onColorSelected = { color ->
                Log.d(logTag, "Color selected: $color")
                profilePictureUrl = null // Clear the image if a color is selected
                profilePictureColor = color
                showColorDialog = false
            },
            onUploadImage = {
                Log.d(logTag, "Launching image picker")
                imagePickerLauncher.launch("image/*")
                showColorDialog = false
            },
            onDismissRequest = {
                Log.d(logTag, "Color picker dialog dismissed")
                showColorDialog = false
            }
        )
    }

    // Main dialog
    AlertDialog(
        onDismissRequest = {
            Log.d(logTag, "Add client dialog dismissed")
            onDismissRequest()
        },
        title = { Text(text = stringResource(R.string.add_client)) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(8.dp))

                // Profile Picture
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    ProfilePicture(
                        profilePictureUrl = profilePictureUrl,
                        initials = getInitials(name),
                        profilePictureColor = profilePictureColor,
                        modifier = Modifier.clickable {
                            Log.d(logTag, "Profile picture clicked")
                            showColorDialog = true
                        }
                    )
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Name input
                CommonOutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        Log.d(logTag, "Name input: $it")
                    },
                    label = stringResource(R.string.name)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Phone input
                CommonOutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        Log.d(logTag, "Phone input: $it")
                    },
                    label = stringResource(R.string.phone),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Email input
                CommonOutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        Log.d(logTag, "Email input: $it")
                    },
                    label = stringResource(R.string.email)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Select contact button
                Button(onClick = {
                    Log.d(logTag, "Selecting contact")
                    contactPickerLauncher.launch(null)
                }) {
                    Text(text = stringResource(R.string.select_from_contacts))
                }
            }
        },
        confirmButton = {
            OkAndCancelButtons(
                onCancelClick = { onDismissRequest() },
                onConfirmClick = {
                    val client = Client(
                        id = 0,
                        name = name,
                        phone = phone,
                        email = email,
                        profilePictureUrl = profilePictureUrl,
                        profilePictureColor = profilePictureColor
                    )
                    Log.d(logTag, "Saving client: $client")
                    sharedClientViewModel.addClient(client)
                    onDismissRequest()
                }
            )
        }
    )
}
