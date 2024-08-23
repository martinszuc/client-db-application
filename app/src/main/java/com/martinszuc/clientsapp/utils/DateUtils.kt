package com.martinszuc.clientsapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Project: Clients database application
 * File: DateUtils
 *
 * Author: Bc. Martin Szuc (matoszuc@gmail.com)
 * GitHub: https://github.com/martinszuc
 *
 * Created on: 8/23/24 at 3:03 PM
 *
 *
 * License:
 * This code is licensed under MIT License. You may not use this file except
 * in compliance with the License.
 */

object DateUtils {
    // Short date format (dd/MM/yy)
    val shortDateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())

    // Long date format (dd/MM/yyyy)
    val longDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Time format (HH:mm)
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun formatShortDate(date: Date): String = shortDateFormat.format(date)
    fun formatLongDate(date: Date): String = longDateFormat.format(date)
    fun formatTime(date: Date): String = timeFormat.format(date)
}