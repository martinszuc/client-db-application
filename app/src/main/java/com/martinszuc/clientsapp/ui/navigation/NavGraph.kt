package com.martinszuc.clientsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.martinszuc.clientsapp.ui.component.client.ClientsScreen
import com.martinszuc.clientsapp.ui.component.client.profile.ClientProfileScreen
import com.martinszuc.clientsapp.ui.component.data.DataScreen
import com.martinszuc.clientsapp.ui.component.menu.MenuScreen
import com.martinszuc.clientsapp.ui.component.search.SearchScreen
import com.martinszuc.clientsapp.ui.component.service.ServicesScreen
import com.martinszuc.clientsapp.ui.component.settings.SettingsScreen

sealed class Screen(val route: String) {
    data object Clients : Screen("clients")
    data object Services : Screen("services")
    data object Search : Screen("search")
    data object Settings : Screen("settings")
    data object Menu : Screen("menu")
    data object Data : Screen("data")
    data class ClientProfile(val clientId: Int) : Screen("clientProfile/$clientId") {
        companion object {
            const val ROUTE = "clientProfile/{clientId}"
        }
    }
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Clients.route,
    onThemeChanged: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Clients.route) {
            ClientsScreen(navController)
        }
        composable(Screen.Services.route) {
            ServicesScreen()
        }
        composable(Screen.Search.route) {
            SearchScreen(navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(onThemeChanged = onThemeChanged)
        }
        composable(Screen.Menu.route) {
            MenuScreen(navController)
        }
        composable(Screen.ClientProfile.ROUTE) { backStackEntry ->
            val clientId =
                backStackEntry.arguments?.getString("clientId")?.toInt() ?: return@composable
            ClientProfileScreen(clientId, navController)
        }
        composable(Screen.Data.route) {
            DataScreen()  // New Data screen
        }
    }
}