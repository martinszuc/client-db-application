package com.martinszuc.clientsapp.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.ui.navigation.NavGraph
import com.martinszuc.clientsapp.ui.navigation.Screen
import com.martinszuc.clientsapp.ui.theme.AppTheme

@Composable
fun MainScreen(initialThemePreference: String) {
    var themePreference by remember { mutableStateOf(initialThemePreference) }
    val isDarkTheme = themePreference == "dark" || (themePreference == "system_default" && isSystemInDarkTheme())

    AppTheme(darkTheme = isDarkTheme) {
        val navController = rememberNavController()
        val bottomNavItems = listOf(
            BottomNavItem(Screen.Clients.route, R.drawable.ic_face, R.string.label_clients),
            BottomNavItem(Screen.Services.route, R.drawable.ic_lipstick_bold, R.string.label_services),
            BottomNavItem(Screen.Search.route, R.drawable.ic_baseline_search_24, R.string.fragment_search),
            BottomNavItem(Screen.Settings.route, R.drawable.ic_baseline_settings_24, R.string.settings)
        )

        Scaffold(
            bottomBar = { BottomNavigationBar(navController, bottomNavItems) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavGraph(navController = navController)
            }
        }
    }
}