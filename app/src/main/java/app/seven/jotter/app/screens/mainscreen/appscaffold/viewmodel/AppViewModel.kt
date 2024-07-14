package app.seven.jotter.app.screens.mainscreen.appscaffold.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {
    private val _appNavigation = Channel<AppNavigation>()
    val appNavigationEvent = _appNavigation.receiveAsFlow()

    fun onAppNavigationAction(action: AppNavigationAction) {

        when (action) {
            is AppNavigationAction.AddTask -> viewModelScope.launch {
                _appNavigation.send(AppNavigation.ShowTaskEditorScreen)
            }

            is AppNavigationAction.Back -> viewModelScope.launch {
                _appNavigation.send(AppNavigation.NavigateBack)
            }
        }
    }
}

sealed interface AppNavigation {
    data object NavigateBack : AppNavigation
    data object ShowTaskEditorScreen : AppNavigation
}

sealed interface AppNavigationAction {
    data object Back : AppNavigationAction
    data object AddTask : AppNavigationAction
}