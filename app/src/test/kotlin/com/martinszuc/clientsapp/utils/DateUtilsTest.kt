package com.martinszuc.clientsapp.utils

/**
 * Project: Clients database application
 * File: DateUtilsTest
 *
 * Author: Bc. Martin Szuc (matoszuc@gmail.com)
 * GitHub: https://github.com/martinszuc
 *
 * Created on: 8/23/24 at 6:46 PM
 *
 *
 * License:
 * This code is licensed under MIT License. You may not use this file except
 * in compliance with the License.
 */


import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class DateUtilsTest {

    @Test
    fun testFormatShortDate() {
        val date = Date(1234567890000)  // Mock timestamp
        val formattedDate = DateUtils.formatShortDate(date)
        assertEquals("14/02/09", formattedDate)
    }

    @Test
    fun testFormatLongDate() {
        val date = Date(1234567890000)
        val formattedDate = DateUtils.formatLongDate(date)
        assertEquals("14/02/2009", formattedDate)
    }

    @Test
    fun testFormatLongDateWithTime() {
        val date = Date(1234567890000)
        val formattedDateTime = DateUtils.formatLongDateWithTime(date)
        assertEquals("14/02/2009 02:31", formattedDateTime)
    }
}