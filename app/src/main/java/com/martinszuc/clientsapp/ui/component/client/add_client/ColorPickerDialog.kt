package com.martinszuc.clientsapp.ui.component.client.add_client

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorPickerDialog(
    onColorSelected: (String) -> Unit,
    onUploadImage: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val logTag = "ColorPickerDialog"
    val colors = listOf(
        "#FF5733", "#33FF57", "#3357FF", "#FF33A1",
        "#FF8C33", "#8C33FF", "#33FFC4", "#FF3380",
        "#3380FF", "#FF5E33"
    )

    AlertDialog(
        onDismissRequest = {
            Log.d(logTag, "Color picker dialog dismissed")
            onDismissRequest()
        },
        title = { Text("Choose Profile Picture") },
        text = {
            Column {
                Text("Select a default color:")
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    for (i in colors.indices step 5) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            colors.subList(i, i + 5.coerceAtMost(colors.size)).forEach { color ->
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(Color(android.graphics.Color.parseColor(color)), CircleShape)
                                        .clickable {
                                            Log.d(logTag, "Color selected: $color")
                                            onColorSelected(color)
                                        }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Or upload an image:")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    Log.d(logTag, "Launching image picker")
                    onUploadImage()
                }) {
                    Text("Choose from gallery")
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                Log.d(logTag, "Done button clicked")
                onDismissRequest()
            }) {
                Text("Done")
            }
        }
    )
}
