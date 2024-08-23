package com.martinszuc.clientsapp.ui.component.client.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.martinszuc.clientsapp.ui.component.profile.ProfilePicture
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel
import com.martinszuc.clientsapp.utils.getInitials
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalPagerApi::class,
    ExperimentalFoundationApi::class
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
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showAddServiceDialog by remember { mutableStateOf(false) } // State to show AddServiceFromProfileDialog
    var showDeleteClientDialog by remember { mutableStateOf(false) }
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

            if (showCallDialog) {
                AlertDialog(
                    onDismissRequest = { showCallDialog = false },
                    title = { Text(text = stringResource(R.string.call_client)) },
                    text = { Text(text = stringResource(R.string.confirm_call, phoneNumber)) },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showCallDialog = false
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                                context.startActivity(intent)
                            }
                        ) {
                            Text(text = stringResource(R.string.yes))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showCallDialog = false }) {
                            Text(text = stringResource(R.string.no))
                        }
                    }
                )
            }

            if (showDeleteClientDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteClientDialog = false },
                    title = { Text(text = stringResource(R.string.delete_client)) },
                    text = { Text(text = stringResource(R.string.confirm_delete_client, client?.name ?: "")) },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                sharedClientViewModel.deleteClient(client!!.id)
                                showDeleteClientDialog = false
                                navController.popBackStack()
                            }
                        ) {
                            Text(text = stringResource(R.string.delete))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteClientDialog = false }) {
                            Text(text = stringResource(R.string.cancel))
                        }
                    }
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
