package com.martinszuc.clientsapp.ui.component.service

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.ui.AppBar
import com.martinszuc.clientsapp.ui.component.service.service_list.ServiceListTab
import com.martinszuc.clientsapp.ui.viewmodel.SharedClientViewModel
import com.martinszuc.clientsapp.ui.viewmodel.SharedServiceViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ServicesScreen(
    navController: NavHostController,  // Accept NavController
    serviceViewModel: SharedServiceViewModel = hiltViewModel(),
    clientViewModel: SharedClientViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { AppBar(title = stringResource(R.string.label_services)) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Tab Row
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    text = { Text(stringResource(R.string.label_list_services)) },
                    selected = pagerState.currentPage == 0,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    }
                )
            }

            // Pager
            HorizontalPager(
                count = 1,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> ServiceListTab(navController, serviceViewModel, clientViewModel)  // Pass NavController
                }
            }
        }
    }
}