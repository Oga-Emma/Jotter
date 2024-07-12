package app.seven.jotter.app.screens.mainscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import app.seven.jotter.app.components.ObserveFlowStateAsEvents
import app.seven.jotter.app.theme.JotterTheme
import app.seven.jotter.app.screens.mainscreen.appscaffold.App
import app.seven.jotter.app.screens.mainscreen.appscaffold.viewmodel.AppUIEvent
import app.seven.jotter.app.screens.mainscreen.appscaffold.viewmodel.AppViewModel
import app.seven.jotter.app.screens.taskeditorscreen.TaskEditorActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            JotterTheme {
                val navController = rememberNavController()
                val appViewModel = hiltViewModel<AppViewModel>()

                ObserveFlowStateAsEvents(flow = appViewModel.uiNavigationEvent) { event ->
                    when (event) {
                        is AppUIEvent.AddTask -> {
                            Intent(this, TaskEditorActivity::class.java)
                                .also {
                                    startActivity(it)
                                }

                        }
                    }
                }

                App(navController = navController, appViewModel = appViewModel)
            }
        }
    }
}
