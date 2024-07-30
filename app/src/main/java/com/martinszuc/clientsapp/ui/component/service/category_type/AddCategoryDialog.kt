package com.martinszuc.clientsapp.ui.component.service.category_type

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.martinszuc.clientsapp.R

@Composable
fun AddCategoryDialog(
    onDismissRequest: () -> Unit,
    onAddCategory: (String, String) -> Unit
) {
    val name = remember { mutableStateOf("") }
    val emoji = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.add_category)) },
        text = {
            Column {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text(stringResource(R.string.category_name)) }
                )
                OutlinedTextField(
                    value = emoji.value,
                    onValueChange = { emoji.value = it },
                    label = { Text(stringResource(R.string.category_emoji)) }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAddCategory(name.value, emoji.value)
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.add))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}