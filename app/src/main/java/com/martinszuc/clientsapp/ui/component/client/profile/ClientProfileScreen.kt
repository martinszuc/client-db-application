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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.martinszuc.clientsapp.ui.component.profile.ProfilePicture
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel
import com.martinszuc.clientsapp.ui.viewmodel.SharedServiceViewModel
import com.martinszuc.clientsapp.util.getInitials
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
    sharedServiceViewModel: SharedServiceViewModel = hiltViewModel()
) {
    val client by sharedClientViewModel.selectedClient.collectAsState()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    var profileCollapsed by remember { mutableStateOf(false) }
    var showCallDialog by remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(clientId) {
        sharedClientViewModel.getClientById(clientId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.client_profile)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more_options)
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                showDeleteDialog = true
                                showMenu = false
                            },
                            text = { Text(text = stringResource(R.string.delete)) }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
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
                            stringResource(R.string.to_do)
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
                        count = 2,
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(600.dp) // Set a fixed height for the pager
                    ) { page ->
                        when (page) {
                            0 -> ProfileServicesTab(clientId)
                            1 -> ProfileTodoTab()
                        }
                    }
                }
            }
        } ?: run {
            Text(
                text = "Client not found",
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
                    TextButton(
                        onClick = { showCallDialog = false }
                    ) {
                        Text(text = stringResource(R.string.no))
                    }
                }
            )
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text(text = stringResource(R.string.delete_client)) },
                text = { Text(text = stringResource(R.string.confirm_delete_client, client?.name ?: "")) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            sharedClientViewModel.deleteClient(client!!.id)
                            showDeleteDialog = false
                            navController.popBackStack()
                        }
                    ) {
                        Text(text = stringResource(R.string.delete))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDeleteDialog = false }
                    ) {
                        Text(text = stringResource(R.string.cancel))
                    }
                }
            )
        }
    }
}
