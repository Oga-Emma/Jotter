package app.seven.jotter.app.screens.taskeditorscreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.seven.jotter.core.models.TaskModel
import app.seven.jotter.core.models.TaskModelCreator
import app.seven.jotter.core.models.TaskReminder
import app.seven.jotter.core.usecases.UpsertTaskUseCase
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

    val task = mutableStateOf(TaskModelCreator.newTaskWithDefaultValues())

    private fun closeDialog() = viewModelScope.launch {
        showDialog(DialogType.NONE, null)
    }

    private fun <T> showDialog(type: DialogType, data: T? = null) = viewModelScope.launch {
        _uiNavigationEvent.send(TaskEditorUIEvents.ShowDialog(type, data))
    }

    private fun updateTask(taskModel: TaskModel) {
        task.value = taskModel
    }

    fun onAction(action: TaskEditorUIActions) {

        Log.d("ON_ACTION", "$action")
        when (action) {
            is TaskEditorUIActions.CloseDialog -> closeDialog()
            is TaskEditorUIActions.SelectCategory -> showDialog(DialogType.CATEGORY, null)
            is TaskEditorUIActions.EditCheckList -> showDialog(DialogType.CHECKLIST, null)
            is TaskEditorUIActions.EditNote -> showDialog(DialogType.NOTE, null)
            is TaskEditorUIActions.SelectPriority -> showDialog(DialogType.PRIORITY, null)
            is TaskEditorUIActions.SelectDate -> showDialog(DialogType.DATE, null)
            is TaskEditorUIActions.EditReminder -> {
                showDialog(DialogType.TIME_REMINDER, null)
            }

            is TaskEditorUIActions.SaveTask -> saveTask(action.data)
            is TaskEditorUIActions.UpdateTask -> {
                viewModelScope.launch {
                    updateTask(action.data)
                }
            }

        }

    }

    private fun saveTask(taskModel: TaskModel) = viewModelScope.launch {
        task.value = taskModel

    }
}

sealed interface TaskEditorUIEvents {
    data class ShowDialog<T>(val dialogType: DialogType, val data: T?) : TaskEditorUIEvents
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

enum class TimeReminderActionType {
    DEFAULT, SET_TIME
}

