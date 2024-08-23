package com.martinszuc.clientsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martinszuc.clientsapp.data.entity.ServiceCategory
import com.martinszuc.clientsapp.data.local.data_repository.ServiceCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceCategoryViewModel @Inject constructor(
    private val repository: ServiceCategoryRepository
) : ViewModel() {
    private val _categories = MutableStateFlow<List<ServiceCategory>>(emptyList())
    val categories: StateFlow<List<ServiceCategory>> = _categories

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = repository.getAllCategories()
        }
    }

    fun addCategory(category: ServiceCategory) {
        viewModelScope.launch {
            repository.insertCategory(category)
            loadCategories()
        }
    }
}
