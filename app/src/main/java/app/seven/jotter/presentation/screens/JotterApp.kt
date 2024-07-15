package app.seven.jotter.presentation.screens

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.seven.jotter.presentation.screens.navigation.JotterNavHost


@Composable
fun JotterApp(
    navController: NavHostController = rememberNavController(),
) {
    JotterNavHost(navController = navController)
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
