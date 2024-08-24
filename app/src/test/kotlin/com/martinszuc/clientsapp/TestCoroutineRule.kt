package com.martinszuc.clientsapp

/**
 * Project: Clients database application
 * File: Test
 *
 * Author: Bc. Martin Szuc (matoszuc@gmail.com)
 * GitHub: https://github.com/martinszuc
 *
 * Created on: 8/24/24 at 11:50 AM
 *
 *
 * License:
 * This code is licensed under MIT License. You may not use this file except
 * in compliance with the License.
 */

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*

import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestWatcher() {
    val testDispatcher = StandardTestDispatcher()

    override fun starting(description: Description?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }
}
