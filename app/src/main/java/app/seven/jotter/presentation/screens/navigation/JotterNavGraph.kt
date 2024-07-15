/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.seven.jotter.presentation.screens.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.seven.jotter.presentation.screens.mainscreen.HomeDestination
import app.seven.jotter.presentation.screens.mainscreen.MainScreen
import app.seven.jotter.presentation.screens.mainscreen.appscaffold.viewmodel.AppNavigation
import app.seven.jotter.presentation.screens.taskeditorscreen.TaskEditorDestination
import app.seven.jotter.presentation.screens.taskeditorscreen.TaskEditorScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun JotterNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    fun handleNavigation(event: AppNavigation) {
        Log.d("JOTTER_NAV_GRAPH", "JotterNavHost: $event")

        when (event) {

            is AppNavigation.ShowTaskEditorScreen -> {
                navController.navigate(TaskEditorDestination.route)
            }

            is AppNavigation.NavigateBack -> navController.popBackStack()
        }
    }

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            MainScreen(onNavigate = ::handleNavigation)
        }
        composable(route = TaskEditorDestination.route) {
            TaskEditorScreen(onNavigate = ::handleNavigation)
        }
//        composable(
//            route = ItemDetailsDestination.routeWithArgs,
//            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
//                type = NavType.IntType
//            })
//        ) {
//            ItemDetailsScreen(
//                navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
//                navigateBack = { navController.navigateUp() }
//            )
//        }
//        composable(
//            route = ItemEditDestination.routeWithArgs,
//            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
//                type = NavType.IntType
//            })
//        ) {
//            ItemEditScreen(
//                navigateBack = { navController.popBackStack() },
//                onNavigateUp = { navController.navigateUp() }
//            )
//        }
    }
}
