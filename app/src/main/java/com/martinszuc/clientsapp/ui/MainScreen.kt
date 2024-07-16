package com.martinszuc.clientsapp.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.martinszuc.clientsapp.R
import com.martinszuc.clientsapp.ui.component.settings.SettingsViewModel
import com.martinszuc.clientsapp.ui.navigation.NavGraph
import com.martinszuc.clientsapp.ui.navigation.Screen
import com.martinszuc.clientsapp.ui.theme.AppTheme
import com.martinszuc.clientsapp.util.AppConstants.THEME_DARK
import com.martinszuc.clientsapp.util.AppConstants.THEME_SYSTEM_DEFAULT

@Composable
fun MainScreen() {
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val themePreference by settingsViewModel.themePreference.collectAsState(initial = THEME_SYSTEM_DEFAULT)

    val isDarkTheme = themePreference == THEME_DARK || (themePreference == THEME_SYSTEM_DEFAULT && isSystemInDarkTheme())

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
                NavGraph(navController = navController,
                    onThemeChanged = { newTheme ->
                        settingsViewModel.changeTheme(newTheme)
                    })
            }
        }
    }
}