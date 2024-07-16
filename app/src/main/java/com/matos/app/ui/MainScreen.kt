package com.matos.app.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.matos.app.R
import com.matos.app.ui.component.client.ClientsScreen
import com.matos.app.ui.component.search.SearchScreen
import com.matos.app.ui.component.service.ServicesScreen
import com.matos.app.ui.component.settings.SettingsScreen
import com.matos.app.ui.theme.AppTheme


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun BottomNavigationBar(navController: NavController, items: List<BottomNavItem>) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(item.icon), contentDescription = stringResource(item.label)) },
                label = { Text(stringResource(item.label)) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Avoid multiple copies of the same destination when reselecting the same item
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val icon: Int,
    val label: Int
)