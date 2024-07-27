package app.seven.jotter.presentation.screens.appscaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.seven.jotter.R
import app.seven.jotter.presentation.screens.appscaffold.appscaffold.components.BottomNavigationBar
import app.seven.jotter.presentation.screens.appscaffold.appscaffold.model.NavItem
import app.seven.jotter.presentation.screens.calendar.CalendarScreen
import app.seven.jotter.presentation.screens.checklist.CheckListScreen
import app.seven.jotter.presentation.screens.today.HomeScreen
import app.seven.jotter.presentation.screens.timer.TimerScreen
import app.seven.jotter.presentation.helpers.NavigationDestination
import app.seven.jotter.presentation.screens.appscaffold.appscaffold.viewmodel.AppNavigationAction

object MainScreenDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    onNavigate: (AppNavigationAction) -> Unit,
) {
    val navItems =
        listOf(NavItem.Home, NavItem.Calendar, NavItem.List, NavItem.Timer)

    Scaffold(bottomBar = {
        BottomAppBar {
            BottomNavigationBar(
                navController = navController, items = navItems
            )
        }
    }) { contentPadding ->

        Surface(
            modifier = Modifier.padding(
                bottom = contentPadding.calculateBottomPadding()
            )
        ) {
            NavHost(
                navController, startDestination = navItems.first().path
            ) {
                composable(
                    route = navItems[0].path,
                    content = {
                        HomeScreen(
                            onNavigationAction = onNavigate
                        )
                    }
                )
                composable(
                    route = navItems[1].path,
                    content = {
                        CalendarScreen()
                    }
                )
                composable(
                    route = navItems[2].path,
                    content = {
                        CheckListScreen()
                    }
                )
                composable(
                    route = navItems[3].path,
                    content = {
                        TimerScreen()
                    }
                )
            }
        }
    }
}