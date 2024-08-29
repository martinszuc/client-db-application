package com.martinszuc.clientsapp.ui.viewmodel

/**
 * Project: Clients database application
 * File: SharedServiceVIewModelTest
 *
 * Author: Bc. Martin Szuc (matoszuc@gmail.com)
 * GitHub: https://github.com/martinszuc
 *
 * Created on: 8/23/24 at 6:50 PM
 *
 *
 * License:
 * This code is licensed under MIT License. You may not use this file except
 * in compliance with the License.
 */

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.martinszuc.clientsapp.TestCoroutineRule
import com.martinszuc.clientsapp.data.entity.Service
import com.martinszuc.clientsapp.data.local.LocalStorageRepository
import com.martinszuc.clientsapp.data.local.data_repository.ServiceRepository
import com.martinszuc.clientsapp.data.remote.FirebaseStorageRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File
import java.util.Date

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)  // Use RobolectricTestRunner for Android tests
class SharedServiceViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Allows LiveData to work synchronously in tests

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()  // Rule for testing coroutines

    private lateinit var viewModel: SharedServiceViewModel
    private val serviceRepository: ServiceRepository = mockk()
    private val localStorageRepository: LocalStorageRepository = mockk()
    private val firebaseStorageRepository: FirebaseStorageRepository = mockk()

    @Before
    fun setup() {
        viewModel = SharedServiceViewModel(serviceRepository, localStorageRepository, firebaseStorageRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()  // Reset main dispatcher after each test
    }

    @Test
    fun `loadServicesIfNotLoaded should load services if not loaded`() = runTest {
        // Mock the repository to return a list of services
        val services = listOf(Service(id = 1, client_id = 1, description = "Test", date = Date(), price = 100.0))
        coEvery { serviceRepository.getServices() } returns services

        // Call the method
        viewModel.loadServicesIfNotLoaded()

        // Ensure the services are loaded correctly
        val loadedServices = viewModel.services.first()
        assertEquals(1, loadedServices.size)
        assertEquals(services[0], loadedServices[0])

        // Verify the repository was called
        coVerify { serviceRepository.getServices() }
    }

    @Test
    fun `addService should insert service and update state`() = runTest {
        val service = Service(id = 1, client_id = 1, description = "New Service", date = Date(), price = 200.0)
        coEvery { serviceRepository.insertService(service) } returns 1L
        coEvery { serviceRepository.getServices() } returns listOf(service)

        // Call the method
        viewModel.addService(service)

        // Check if the service is added and state updated
        val loadedServices = viewModel.services.first()
        assertEquals(1, loadedServices.size)
        assertEquals(service, loadedServices[0])

        // Verify repository calls
        coVerify(exactly = 1) { serviceRepository.insertService(service) }
        coVerify(exactly = 1) { serviceRepository.getServices() }
    }

    @Test
    fun `addServiceWithPhotos should insert service and upload photos`() = runTest {
        val service = Service(id = 1, client_id = 1, description = "Test Service", date = Date(), price = 100.0)
        val uri = mockk<Uri>(relaxed = true)
        val file = mockk<File>(relaxed = true)
        val fileUri = Uri.fromFile(file)

        // Mock repository calls
        coEvery { serviceRepository.insertService(service) } returns 1L
        coEvery { localStorageRepository.savePhotoToInternalStorage(uri, any(), any()) } returns file
        coEvery { firebaseStorageRepository.uploadPhotoWithDirectory(fileUri, any(), any()) } returns "downloadUrl"
        coEvery { serviceRepository.insertPhoto(any()) } just Runs

        // Call the method
        viewModel.addServiceWithPhotos(service, listOf(uri), onUploadFailure = {})

        // Verify interactions
        coVerify(exactly = 1) { serviceRepository.insertService(service) }
        coVerify(exactly = 1) { localStorageRepository.savePhotoToInternalStorage(uri, any(), any()) }
        coVerify(exactly = 1) { firebaseStorageRepository.uploadPhotoWithDirectory(fileUri, any(), any()) }
        coVerify(exactly = 1) { serviceRepository.insertPhoto(any()) }
    }

    @Test
    fun `deleteService should remove service and associated photos`() = runTest {
        val service = Service(id = 1, client_id = 1, description = "Test Service", date = Date(), price = 100.0)

        // Mock repository calls
        coEvery { serviceRepository.deletePhotosForService(service.id) } just Runs
        coEvery { serviceRepository.deleteService(service) } just Runs

        // Call the method
        var onDeleteSuccessCalled = false
        viewModel.deleteService(service, onDeleteSuccess = {
            onDeleteSuccessCalled = true
        }, onDeleteFailure = {})

        // Verify interactions
        coVerify(exactly = 1) { serviceRepository.deletePhotosForService(service.id) }
        coVerify(exactly = 1) { serviceRepository.deleteService(service) }

        // Check if the success callback is called
        assert(onDeleteSuccessCalled)
    }
}
