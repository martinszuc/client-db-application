package com.martinszuc.clientsapp.utils

fun getInitials(name: String): String {
    val parts = name.split(" ")
    return when (parts.size) {
        0 -> ""
        1 -> parts[0].take(2).uppercase()
        else -> (parts[0].take(1) + parts[1].take(1)).uppercase()
    }
}