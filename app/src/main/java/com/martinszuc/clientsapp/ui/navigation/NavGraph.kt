package com.martinszuc.clientsapp.ui.navigation

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
import com.martinszuc.clientsapp.ui.component.service.service_details.ServiceProfileScreen
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
    // Service Profile Screen with serviceId parameter
    data class ServiceProfile(val serviceId: Int) : Screen("serviceDetails/$serviceId") {
        companion object {
            const val ROUTE = "serviceDetails/{serviceId}"
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
            ServicesScreen(navController = navController)
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
            DataScreen()
        }

        // Ensure that the Service Profile Screen is added
        composable(Screen.ServiceProfile.ROUTE) { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getString("serviceId")?.toIntOrNull()
            if (serviceId != null) {
                ServiceProfileScreen(serviceId = serviceId, navController = navController)
            }
        }
    }
}