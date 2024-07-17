package app.seven.jotter.presentation.screens.mainscreen.appscaffold.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {
    private val _appNavigationEvent = Channel<AppNavigationEvent>()
    val appNavigationEventFlow = _appNavigationEvent.receiveAsFlow()

    private val _popupMessageEvent = Channel<PopupMessageEvent>()
    val popupMessageEvent = _popupMessageEvent.receiveAsFlow()

    fun navigate(to: AppNavigationAction) {
        when (to) {
            is AppNavigationAction.AddTaskScreen -> viewModelScope.launch {
                _appNavigationEvent.send(AppNavigationEvent.ShowTaskEditorScreen)
            }

            is AppNavigationAction.PreviousScreen -> viewModelScope.launch {
                _appNavigationEvent.send(AppNavigationEvent.NavigateBack)
            }
        }
    }

    fun showSnackBar(to: PopupMessageAction) {
        when (to) {
            is PopupMessageAction.Toast -> viewModelScope.launch {
                _popupMessageEvent.send(
                    PopupMessageEvent.Toast(to.message)
                )
            }
        }
    }
}

sealed interface AppNavigationEvent {
    data object NavigateBack : AppNavigationEvent
    data object ShowTaskEditorScreen : AppNavigationEvent
}

sealed interface AppNavigationAction {
    data object PreviousScreen : AppNavigationAction
    data object AddTaskScreen : AppNavigationAction
}

sealed interface PopupMessageEvent {
    data class Toast(val message: String) : PopupMessageEvent
}

sealed interface PopupMessageAction {
    data class Toast(val message: String) : PopupMessageAction
}
