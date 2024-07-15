package com.matos.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.matos.app.R
import com.matos.app.ui.component.client.ClientsScreen
import com.matos.app.ui.component.search.SearchScreen
import com.matos.app.ui.component.service.ServicesScreen
import com.matos.app.ui.component.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val bottomNavItems = listOf(
        BottomNavItem("clients", R.drawable.ic_face, R.string.label_clients),
        BottomNavItem("search", R.drawable.ic_baseline_search_24, R.string.fragment_search),
        BottomNavItem("services", R.drawable.ic_lipstick_bold, R.string.label_services),
        BottomNavItem("settings", R.drawable.ic_baseline_settings_24, R.string.settings)
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("My Application") }) },
        bottomBar = { BottomNavigationBar(navController, bottomNavItems) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = "clients") {
                composable("clients") { ClientsScreen() }
                composable("search") { SearchScreen() }
                composable("services") { ServicesScreen() }
                composable("settings") { SettingsScreen() }
            }
        }
    }
}