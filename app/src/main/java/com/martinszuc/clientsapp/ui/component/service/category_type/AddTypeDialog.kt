package com.martinszuc.clientsapp.ui.component.service.category_type

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.data.entity.ServiceCategory

@Composable
fun AddTypeDialog(
    categories: List<ServiceCategory>,
    onDismissRequest: () -> Unit,
    onAddType: (String, String, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var emoji by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(0) } // Default to 0, assuming 0 is not a valid category ID
    var emojiError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.add_type)) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.type_name)) }
                )
                OutlinedTextField(
                    value = emoji,
                    onValueChange = {
                        emoji = it
                        emojiError = emoji.any { char -> !Character.isDefined(char) || Character.isISOControl(char) }
                    },
                    label = { Text(stringResource(R.string.category_emoji)) },
                    isError = emojiError,
                    modifier = Modifier.padding(top = 8.dp)
                )
                if (emojiError) {
                    Text(
                        text = stringResource(R.string.emoji_error),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = stringResource(R.string.category_label))
                    Button(
                        onClick = { expanded = true },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        val selectedCategoryObject = categories.find { it.id == selectedCategory }
                        val categoryText = selectedCategoryObject?.let {
                            "${it.emoji} ${it.name}"
                        } ?: stringResource(R.string.pick_category)
                        Text(text = categoryText)
                    }
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            onClick = {
                                selectedCategory = category.id
                                expanded = false
                            },
                            text = { Text(text = "${category.emoji} ${category.name}") }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (!emojiError && emoji.isNotEmpty() && selectedCategory != 0) {
                        onAddType(name, emoji, selectedCategory)
                        onDismissRequest()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(stringResource(R.string.add), color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest,
            ) {
                Text(stringResource(R.string.cancel), color = MaterialTheme.colorScheme.onSecondary)
            }
        }
    )
}
