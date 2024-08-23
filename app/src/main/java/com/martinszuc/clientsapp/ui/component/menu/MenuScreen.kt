package com.martinszuc.clientsapp.ui.component.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.ui.component.common.AppBarWithoutActions
import com.martinszuc.clientsapp.ui.component.common.SquareButtonWithIcon
import com.martinszuc.clientsapp.ui.navigation.Screen

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

@Composable
fun MenuScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            AppBarWithoutActions(
                title = stringResource(R.string.label_menu)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Adjust the grid arrangement to display buttons in rows of 2
            val buttons = listOf(
                Triple(
                    stringResource(R.string.search),
                    Screen.Search.route,
                    painterResource(R.drawable.ic_baseline_search_24)
                ),
                Triple(
                    stringResource(R.string.data),
                    Screen.Data.route,
                    painterResource(R.drawable.ic_chart)
                ) // New Data screen button with a different icon
                // Add more buttons here with appropriate icons as needed
            )

            buttons.chunked(2).forEach { rowButtons ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowButtons.forEach { (label, route, icon) ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            SquareButtonWithIcon(
                                label = label,
                                route = route,
                                icon = icon,
                                navController = navController
                            )
                        }
                    }
                    // Add a Spacer to fill the row if there's an odd number of buttons
                    if (rowButtons.size == 1) {
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}