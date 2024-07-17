package app.seven.jotter.presentation.screens.taskeditorscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.seven.jotter.core.models.TaskModel
import app.seven.jotter.core.models.TaskModelCreator
import app.seven.jotter.usecases.UpsertTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskEditorViewModel @Inject constructor(
    private val upsertTaskUseCase: UpsertTaskUseCase
) : ViewModel() {
    private val _uiNavigationEvent = Channel<TaskEditorUIEvents>()
    val uiNavigationEvent = _uiNavigationEvent.receiveAsFlow()

    var task by mutableStateOf(TaskModelCreator.newTaskWithDefaultValues())

    private fun closeDialog() = showDialog(DialogType.NONE)

    private fun showDialog(type: DialogType) = viewModelScope.launch {
        _uiNavigationEvent.send(TaskEditorUIEvents.ShowDialog(type))
    }

    private fun updateTask(taskModel: TaskModel) {
        task = taskModel
    }

    fun onAction(action: TaskEditorUIActions) {
        when (action) {
            is TaskEditorUIActions.CloseDialog -> closeDialog()
            is TaskEditorUIActions.SelectCategory -> showDialog(DialogType.CATEGORY)
            is TaskEditorUIActions.EditCheckList -> showDialog(DialogType.CHECKLIST)
            is TaskEditorUIActions.EditNote -> showDialog(DialogType.NOTE)
            is TaskEditorUIActions.SelectPriority -> showDialog(DialogType.PRIORITY)
            is TaskEditorUIActions.SelectDate -> showDialog(DialogType.DATE)
            is TaskEditorUIActions.EditReminder -> showDialog(DialogType.TIME_REMINDER)
            is TaskEditorUIActions.SaveTask -> saveTask(action.data)
            is TaskEditorUIActions.UpdateTask -> updateTask(action.data)
        }
    }

    private fun saveTask(taskModel: TaskModel) = viewModelScope.launch {
        task = taskModel
    }
}

sealed interface TaskEditorUIEvents {
    data class ShowDialog(val dialogType: DialogType) : TaskEditorUIEvents
}

sealed interface TaskEditorUIActions {
    data object SelectCategory : TaskEditorUIActions
    data object SelectPriority : TaskEditorUIActions
    data object EditNote : TaskEditorUIActions
    data object EditCheckList : TaskEditorUIActions
    data object SelectDate : TaskEditorUIActions
    data object CloseDialog : TaskEditorUIActions
    data class UpdateTask(val data: TaskModel) : TaskEditorUIActions
    data class SaveTask(val data: TaskModel) : TaskEditorUIActions
    data object EditReminder : TaskEditorUIActions
}

enum class DialogType {
    NONE, CATEGORY, DATE, TIME_REMINDER, PRIORITY, NOTE, CHECKLIST
}

