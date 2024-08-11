package app.seven.jotter.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.seven.jotter.presentation.helpers.ObserveFlowStateAsEvents
import app.seven.jotter.presentation.screens.appscaffold.MainScreenDestination
import app.seven.jotter.presentation.screens.appscaffold.MainScreen
import app.seven.jotter.presentation.screens.appscaffold.appscaffold.viewmodel.AppNavigationEvent
import app.seven.jotter.presentation.screens.appscaffold.appscaffold.viewmodel.AppViewModel
import app.seven.jotter.presentation.screens.appscaffold.appscaffold.viewmodel.PopupMessageEvent
import app.seven.jotter.presentation.screens.taskeditor.TaskEditorDestination
import app.seven.jotter.presentation.screens.taskeditor.TaskEditorScreen


@Composable
fun JotterApp(
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    val appViewModel = hiltViewModel<AppViewModel>()

    ObserveFlowStateAsEvents(appViewModel.appNavigationEventFlow) { navigationEvent ->
        when (navigationEvent) {
            is AppNavigationEvent.ShowTaskEditorScreen -> {
                navController.navigate(TaskEditorDestination.route)
            }

            is AppNavigationEvent.NavigateBack -> navController.popBackStack()
        }
    }

    ObserveFlowStateAsEvents(appViewModel.popupMessageEvent) { event ->
        when (event) {
            is PopupMessageEvent.Toast -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Nav host
    NavHost(
        navController = navController,
        startDestination = MainScreenDestination.route,
    ) {
        composable(route = MainScreenDestination.route) {
            MainScreen(
                onNavigate = appViewModel::navigate
            )
        }
        composable(route = TaskEditorDestination.route) {
            TaskEditorScreen(
                onNavigationAction = appViewModel::navigate,
                onShowPopupMessage = appViewModel::showSnackBar
            )
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

/**
 * App bar to display title and conditionally display the back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JotterTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    centerAligned: Boolean = false,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {

    if (centerAligned) {
        CenterAlignedTopAppBar(
            modifier = modifier,
            title = { Text(title) },
            scrollBehavior = scrollBehavior,
            actions = actions,
            navigationIcon = {
                NavigationIcon(
                    canNavigateBack = canNavigateBack,
                    navigateUp = navigateUp
                )
            }
        )
    } else {
        TopAppBar(
            modifier = modifier,
            scrollBehavior = scrollBehavior,
            title = { Text(text = title) },
            navigationIcon = {
                NavigationIcon(
                    canNavigateBack = canNavigateBack,
                    navigateUp = navigateUp
                )
            },
            actions = actions
        )
    }
}

@Composable
fun NavigationIcon(canNavigateBack: Boolean, navigateUp: () -> Unit) {
    if (canNavigateBack) {
        IconButton(onClick = navigateUp) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = ""//stringResource(string.back_button)
            )
        }
    }
}
