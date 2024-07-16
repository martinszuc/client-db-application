package com.martinszuc.clientsapp.ui.component.client

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.martinszuc.clientsapp.data.entity.Client
import com.martinszuc.clientsapp.ui.component.profile.ProfilePicture

@Composable
fun ClientItem(client: Client, onClick: (Client) -> Unit) {
    val logTag = "ClientItem"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                Log.d(logTag, "Client item clicked: ${client.id}")
                onClick(client)
            },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            ProfilePicture(
                profilePictureUrl = client.profilePictureUrl,
                initials = client.name.take(2),
                profilePictureColor = client.profilePictureColor,
                size = 40
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = client.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = client.email ?: "No email", style = MaterialTheme.typography.bodyMedium)
                Text(text = client.phone ?: "No phone", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}