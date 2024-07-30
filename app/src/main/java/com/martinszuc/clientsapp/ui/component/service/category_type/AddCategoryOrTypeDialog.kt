package com.martinszuc.clientsapp.ui.component.service.category_type

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
                Row(
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                ) {
                    Button(
                        onClick = {
                            onDismissRequest()
                            onAddCategory()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp) // Adjust padding between buttons
                    ) {
                        Text(stringResource(R.string.add_category))
                    }
                    Button(
                        onClick = {
                            onDismissRequest()
                            onAddType()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp) // Adjust padding between buttons
                    ) {
                        Text(stringResource(R.string.add_type))
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = onDismissRequest,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
