package com.martinszuc.clientsapp.ui.component.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfilePicture(
    profilePictureUrl: String?,
    initials: String,
    profilePictureColor: String?,
    modifier: Modifier = Modifier,
    size: Int = 100
) {
    Surface(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape),
        shape = CircleShape,
        color = profilePictureColor?.let { Color(android.graphics.Color.parseColor(it)) }
            ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    ) {
        if (profilePictureUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(profilePictureUrl),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = initials,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }
        }
    }
}