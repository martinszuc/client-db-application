package com.martinszuc.clientsapp.ui.component.service.service_type_manager


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.data.entity.ServiceCategory
import com.martinszuc.clientsapp.data.entity.ServiceType
import com.martinszuc.clientsapp.ui.component.service.category_type.AddCategoryDialog
import com.martinszuc.clientsapp.ui.component.service.category_type.AddCategoryOrTypeDialog
import com.martinszuc.clientsapp.ui.component.service.category_type.AddTypeDialog
import com.martinszuc.clientsapp.ui.component.service.category_type.CategoryItem
import com.martinszuc.clientsapp.ui.viewmodel.ServiceCategoryViewModel
import com.martinszuc.clientsapp.ui.viewmodel.ServiceTypeViewModel
import kotlinx.coroutines.launch

@Composable
fun ServiceTypeManagementTab(
    categoryViewModel: ServiceCategoryViewModel = hiltViewModel(),
    typeViewModel: ServiceTypeViewModel = hiltViewModel()
) {
    val categories by categoryViewModel.categories.collectAsState()
    val types by typeViewModel.serviceTypes.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var showAddDialog by remember { mutableStateOf(false) }
    var showCategoryDialog by remember { mutableStateOf(false) }
    var showTypeDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(categories) { category ->
                    CategoryItem(
                        category = category,
                        types = types.filter { it.category_id == category.id }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_category_or_type))
        }

        if (showAddDialog) {
            AddCategoryOrTypeDialog(
                onDismissRequest = { showAddDialog = false },
                onAddCategory = { showCategoryDialog = true },
                onAddType = { showTypeDialog = true }
            )
        }

        if (showCategoryDialog) {
            AddCategoryDialog(
                onDismissRequest = { showCategoryDialog = false },
                onAddCategory = { name, emoji ->
                    coroutineScope.launch {
                        categoryViewModel.addCategory(ServiceCategory(0, name, emoji))
                    }
                    showCategoryDialog = false
                }
            )
        }

        if (showTypeDialog) {
            AddTypeDialog(
                categories = categories,
                onDismissRequest = { showTypeDialog = false },
                onAddType = { name, emoji, categoryId ->
                    coroutineScope.launch {
                        typeViewModel.addType(ServiceType(0, name, emoji, categoryId))
                    }
                    showTypeDialog = false
                }
            )
        }
    }
}