package com.martinszuc.clientsapp.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.ui.component.client.ClientsScreen
import com.martinszuc.clientsapp.ui.component.search.SearchScreen
import com.martinszuc.clientsapp.ui.component.service.ServicesScreen
import com.martinszuc.clientsapp.ui.component.settings.SettingsScreen
import com.martinszuc.clientsapp.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(initialThemePreference: String) {
    var themePreference by remember { mutableStateOf(initialThemePreference) }
    val isDarkTheme = themePreference == "dark" || (themePreference == "system_default" && isSystemInDarkTheme())

    AppTheme(darkTheme = isDarkTheme) {
        val navController = rememberNavController()
        val bottomNavItems = listOf(
            BottomNavItem("clients", R.drawable.ic_face, R.string.label_clients),
            BottomNavItem("search", R.drawable.ic_baseline_search_24, R.string.fragment_search),
            BottomNavItem("services", R.drawable.ic_lipstick_bold, R.string.label_services),
            BottomNavItem("settings", R.drawable.ic_baseline_settings_24, R.string.settings)
        )

        Scaffold(
            topBar = { AppBar(title = "App") },
            bottomBar = { BottomNavigationBar(navController, bottomNavItems) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(navController = navController, startDestination = "clients") {
                    composable("clients") { ClientsScreen() }
                    composable("search") { SearchScreen() }
                    composable("services") { ServicesScreen() }
                    composable("settings") { SettingsScreen(onThemeChanged = { newTheme ->
                        themePreference = newTheme
                    }) }
                }
            }
        }
    }
}