package com.martinszuc.clientsapp.ui.component.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.ui.component.common.AppBarWithoutActions
import com.martinszuc.clientsapp.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
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
                Triple(stringResource(R.string.search), Screen.Search.route, painterResource(R.drawable.ic_baseline_search_24)),
                Triple(stringResource(R.string.data), Screen.Data.route, painterResource(R.drawable.ic_chart)) // New Data screen button with a different icon
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
                            SquareButton(
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

@Composable
fun SquareButton(
    label: String,
    route: String,
    icon: Painter,
    navController: NavHostController,
    shape: Shape = RectangleShape
) {
    Button(
        onClick = { navController.navigate(route) },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f), // Ensure the Button is square
        shape = shape // Apply the custom shape
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = icon, // Use the provided icon
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = label)
        }
    }
}
