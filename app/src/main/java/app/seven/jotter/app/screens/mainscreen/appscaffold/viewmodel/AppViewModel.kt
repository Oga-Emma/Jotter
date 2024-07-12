package app.seven.jotter.app.screens.mainscreen.appscaffold.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.seven.jotter.app.screens.mainscreen.appscaffold.viewmodel.AppAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {
    private val _uiNavigationEvent = Channel<AppUIEvent>()
    val uiNavigationEvent = _uiNavigationEvent.receiveAsFlow()

    fun onAction(action: AppAction) {
        when (action) {
            AppAction.AddTask -> viewModelScope.launch {
                _uiNavigationEvent.send(AppUIEvent.AddTask)
            }
            else -> Unit
        }


    }
}

sealed interface AppUIEvent {
    data object AddTask : AppUIEvent
}

sealed interface AppAction {
    data object AddTask : AppAction
}