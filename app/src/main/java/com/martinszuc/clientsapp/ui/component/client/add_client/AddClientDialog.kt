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
import androidx.compose.material3.OutlinedTextField
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
import com.martinszuc.clientsapp.ui.component.common.CommonCancelButton
import com.martinszuc.clientsapp.ui.component.common.CommonOkButton
import com.martinszuc.clientsapp.ui.component.profile.ProfilePicture
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel
import com.martinszuc.clientsapp.util.getContactInfo
import com.martinszuc.clientsapp.util.getInitials

@Composable
fun AddClientDialog(
    onDismissRequest: () -> Unit,
    sharedClientViewModel: SharedClientViewModel = hiltViewModel(),
    context: Context
) {
    val logTag = "AddClientDialog"

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var profilePictureUrl by remember { mutableStateOf<String?>(null) }
    var profilePictureColor by remember { mutableStateOf<String?>(null) }
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
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        Log.d(logTag, "Name input: $it")
                    },
                    label = { Text(text = stringResource(R.string.name)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Phone input
                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        Log.d(logTag, "Phone input: $it")
                    },
                    label = { Text(text = stringResource(R.string.phone)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Email input
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        Log.d(logTag, "Email input: $it")
                    },
                    label = { Text(text = stringResource(R.string.email)) },
                    modifier = Modifier.fillMaxWidth()
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
        // Confirm (OK) button
        confirmButton = {
            CommonOkButton(onClick = {
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
            })
        },
        // Dismiss (Cancel) button
        dismissButton = {
            CommonCancelButton(onClick = { onDismissRequest() })
        }
    )
}