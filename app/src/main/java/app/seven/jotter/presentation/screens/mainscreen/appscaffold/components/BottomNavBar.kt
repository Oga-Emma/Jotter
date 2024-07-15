package app.seven.jotter.presentation.screens.mainscreen.appscaffold.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import app.seven.jotter.presentation.screens.mainscreen.appscaffold.model.NavigationItem

@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<NavigationItem>) {
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, item ->
            val selected = selectedItem == index

            NavigationBarItem(
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = selected,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.path) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
