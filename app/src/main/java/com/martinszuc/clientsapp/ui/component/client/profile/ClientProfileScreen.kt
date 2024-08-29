package com.martinszuc.clientsapp.ui.component.client.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.ui.component.client.profile.add_service.AddServiceFromProfileDialog
import com.martinszuc.clientsapp.ui.component.client.profile.services_list.ProfileServicesTab
import com.martinszuc.clientsapp.ui.component.common.AppBarWithBackButtonAndActions
import com.martinszuc.clientsapp.ui.component.common.ProfilePicture
import com.martinszuc.clientsapp.ui.component.common.dialogs.ConfirmationDialog
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel
import com.martinszuc.clientsapp.utils.getInitials
import kotlinx.coroutines.launch

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

@OptIn(
    ExperimentalPagerApi::class, ExperimentalFoundationApi::class
)
@Composable
fun ClientProfileScreen(
    clientId: Int,
    navController: NavHostController,
    sharedClientViewModel: SharedClientViewModel = hiltViewModel(),
) {
    val client by sharedClientViewModel.selectedClient.collectAsState()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    var profileCollapsed by remember { mutableStateOf(false) }
    var showCallDialog by remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }
    var showDeleteClientDialog by remember { mutableStateOf(false) }
    var showAddServiceDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(clientId) {
        sharedClientViewModel.getClientById(clientId)
    }

    Scaffold(
        topBar = {
            AppBarWithBackButtonAndActions(
                title = stringResource(R.string.client_profile),
                onBackClick = { navController.popBackStack() },
                actions = {
                    // Action Buttons
                    IconButton(onClick = { showAddServiceDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_service),
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more_options),
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                showDeleteClientDialog = true
                                showMenu = false
                            },
                            text = { Text(text = stringResource(R.string.delete)) }
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            client?.let { client ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (!profileCollapsed) {
                                ProfilePicture(
                                    profilePictureUrl = client.profilePictureUrl,
                                    initials = getInitials(client.name),
                                    profilePictureColor = client.profilePictureColor
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            Text(text = client.name, style = MaterialTheme.typography.titleLarge)
                            Text(
                                text = client.email ?: "No email",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = client.phone ?: "No phone",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.clickable {
                                    client.phone?.let {
                                        phoneNumber = it
                                        showCallDialog = true
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    stickyHeader {
                        TabRow(
                            selectedTabIndex = pagerState.currentPage,
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = contentColorFor(MaterialTheme.colorScheme.surface)
                        ) {
                            listOf(
                                stringResource(R.string.label_services),
                            ).forEachIndexed { index, title ->
                                Tab(
                                    text = { Text(title) },
                                    selected = pagerState.currentPage == index,
                                    onClick = {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    }
                                )
                            }
                        }
                    }

                    item {
                        HorizontalPager(
                            count = 1,
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(600.dp) // Set a fixed height for the pager
                        ) { page ->
                            when (page) {
                                0 -> ProfileServicesTab(clientId, navController)
                            }
                        }
                    }
                }
            } ?: run {
                Text(
                    text = stringResource(R.string.clients_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            // Call Client Dialog
            if (showCallDialog) {
                ConfirmationDialog(
                    title = stringResource(R.string.call_client),
                    message = stringResource(R.string.confirm_call, phoneNumber),
                    onConfirm = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                        context.startActivity(intent)
                    },
                    onDismiss = { showCallDialog = false }
                )
            }

            // Delete Client Dialog
            if (showDeleteClientDialog) {
                ConfirmationDialog(
                    title = stringResource(R.string.delete_client),
                    message = stringResource(R.string.confirm_delete_client, client?.name ?: ""),
                    onConfirm = {
                        sharedClientViewModel.deleteClient(client!!.id)
                        navController.popBackStack()
                    },
                    onDismiss = { showDeleteClientDialog = false }
                )
            }

            // Show AddServiceFromProfileDialog
            if (showAddServiceDialog) {
                AddServiceFromProfileDialog(
                    clientId = clientId,
                    onDismissRequest = { showAddServiceDialog = false }
                )
            }
        }
    )
}
