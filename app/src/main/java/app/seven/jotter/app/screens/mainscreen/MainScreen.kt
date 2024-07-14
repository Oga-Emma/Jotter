package app.seven.jotter.app.screens.mainscreen

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.seven.jotter.R
import app.seven.jotter.app.components.ObserveFlowStateAsEvents
import app.seven.jotter.app.screens.mainscreen.appscaffold.components.BottomNavigationBar
import app.seven.jotter.app.screens.mainscreen.appscaffold.model.NavItem
import app.seven.jotter.app.screens.mainscreen.appscaffold.viewmodel.AppNavigation
import app.seven.jotter.app.screens.mainscreen.appscaffold.viewmodel.AppViewModel
import app.seven.jotter.app.screens.mainscreen.calendar.CalendarScreen
import app.seven.jotter.app.screens.mainscreen.checklist.CheckListScreen
import app.seven.jotter.app.screens.mainscreen.home.HomeScreen
import app.seven.jotter.app.screens.mainscreen.timer.TimerScreen
import com.example.inventory.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun MainScreen(
    appViewModel: AppViewModel = hiltViewModel<AppViewModel>(),
    navController: NavHostController = rememberNavController(),
    onNavigate: (AppNavigation) -> Unit
) {
    val navItems =
        listOf(NavItem.Home, NavItem.Calendar, NavItem.List, NavItem.Timer)

    ObserveFlowStateAsEvents(flow = appViewModel.appNavigationEvent, onEvent = onNavigate)

    Scaffold(bottomBar = {
        BottomAppBar {
            BottomNavigationBar(
                navController = navController, items = navItems
            )
        }
    }) { contentPadding ->

        Surface {
            NavHost(
                navController, startDestination = navItems.first().path
            ) {
                composable(
                    route = navItems[0].path,
                    content = {
                        HomeScreen(
                            onAppNavigationAction = appViewModel::onAppNavigationAction
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