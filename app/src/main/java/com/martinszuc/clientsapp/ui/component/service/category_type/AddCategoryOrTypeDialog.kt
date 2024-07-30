package com.martinszuc.clientsapp.ui.component.service.category_type

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.martinszuc.clientsapp.R

@Composable
fun AddCategoryOrTypeDialog(
    onDismissRequest: () -> Unit,
    onAddCategory: () -> Unit,
    onAddType: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.add_category_or_type)) },
        text = {
            Column {
                TextButton(onClick = onAddCategory) {
                    Text(stringResource(R.string.add_category))
                }
                TextButton(onClick = onAddType) {
                    Text(stringResource(R.string.add_type))
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}