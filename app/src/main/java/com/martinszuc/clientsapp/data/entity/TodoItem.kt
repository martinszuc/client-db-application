package com.martinszuc.clientsapp.data.entity

/**
 * Project: database application
 *
 * Author: Bc. Martin Szuc (matoszuc@gmail.com)
 * GitHub: https://github.com/martinszuc
 *
 *
 * License:
 * This code is licensed under MIT License. You may not use this file except
 * in compliance with the License.
 */

data class TodoItem(
    val id: Int,
    val name: String,
    val description: String
)