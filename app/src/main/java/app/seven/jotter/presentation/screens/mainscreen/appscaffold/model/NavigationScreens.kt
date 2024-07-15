package app.seven.jotter.presentation.screens.mainscreen.appscaffold.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.DashboardCustomize
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.DashboardCustomize
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.ui.graphics.vector.ImageVector


open class NavigationItem(
    val path: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,

    )

enum class NavPath {
    HOME, CALENDAR, LIST, TIMER
}

sealed class NavItem {
    object Home :
        NavigationItem(
            path = NavPath.HOME.toString(),
            title = NavTitle.HOME,
            selectedIcon = Icons.Filled.DashboardCustomize,
            unselectedIcon = Icons.Outlined.DashboardCustomize,
        )

    object Calendar :
        NavigationItem(
            path = NavPath.CALENDAR.toString(),
            title = NavTitle.CALENDAR,
            selectedIcon = Icons.Filled.CalendarMonth,
            unselectedIcon = Icons.Outlined.CalendarMonth,
        )

    object List :
        NavigationItem(
            path = NavPath.LIST.toString(),
            title = NavTitle.LIST,
            selectedIcon = Icons.Filled.Checklist,
            unselectedIcon = Icons.Outlined.Checklist,
        )

    object Timer :
        NavigationItem(
            path = NavPath.TIMER.toString(), title = NavTitle.TIMER,
            selectedIcon = Icons.Filled.Timer,
            unselectedIcon = Icons.Outlined.Timer,
        )
}

object NavTitle {
    const val HOME = "Today"
    const val CALENDAR = "Calender"
    const val LIST = "Checklist"
    const val TIMER = "Timer"
}
