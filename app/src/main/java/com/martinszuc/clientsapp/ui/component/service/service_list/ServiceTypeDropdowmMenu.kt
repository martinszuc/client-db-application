package com.martinszuc.clientsapp.ui.component.service.service_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.data.entity.ServiceCategory
import com.martinszuc.clientsapp.data.entity.ServiceType

@Composable
fun ServiceTypeDropdownMenu(
    categories: List<ServiceCategory>,
    types: List<ServiceType>,
    selectedCategoryId: Int?,
    selectedTypeId: Int?,
    onCategorySelected: (Int) -> Unit,
    onTypeSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategoryName by remember { mutableStateOf("") }
    var selectedTypeName by remember { mutableStateOf("") }
    val expandedCategories = remember { mutableStateMapOf<Int, Boolean>() }

    LaunchedEffect(selectedCategoryId) {
        selectedCategoryName = categories.find { it.id == selectedCategoryId }?.name ?: ""
    }

    LaunchedEffect(selectedTypeId) {
        selectedTypeName = types.find { it.id == selectedTypeId }?.name ?: ""
    }

    Box(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
        Column {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (selectedCategoryId != null && selectedTypeId != null) "$selectedCategoryName - $selectedTypeName" else stringResource(
                    R.string.select_service_type)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        onClick = {
                            expandedCategories[category.id] = expandedCategories[category.id] != true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.tertiaryContainer),
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "${category.emoji} ${category.name}", modifier = Modifier.weight(1f))
                                Icon(
                                    imageVector = if (expandedCategories[category.id] == true) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                    if (expandedCategories[category.id] == true) {
                        types.filter { it.category_id == category.id }.forEach { type ->
                            Divider(modifier = Modifier.padding(start = 16.dp))
                            DropdownMenuItem(
                                onClick = {
                                    onCategorySelected(category.id)
                                    onTypeSelected(type.id)
                                    selectedCategoryName = category.name
                                    selectedTypeName = type.name
                                    expanded = false
                                },
                                modifier = Modifier.fillMaxWidth(),
                                text = {
                                    Text(text = "    ${type.emoji} ${type.name}")
                                }
                            )
                        }
                    }
                    Divider()
                }
            }
        }
    }
}
