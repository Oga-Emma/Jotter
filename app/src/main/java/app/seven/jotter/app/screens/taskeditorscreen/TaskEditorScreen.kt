package app.seven.jotter.app.screens.taskeditorscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import app.seven.jotter.R
import app.seven.jotter.app.components.CustomDatePikerDialog
import app.seven.jotter.app.components.ObserveFlowStateAsEvents
import app.seven.jotter.app.components.keyboardAsState
import app.seven.jotter.app.screens.taskeditorscreen.component.EditTaskReminder
import app.seven.jotter.app.screens.taskeditorscreen.dialogs.TaskCategoryDialog
import app.seven.jotter.app.screens.taskeditorscreen.dialogs.TaskCheckListDialog
import app.seven.jotter.app.screens.taskeditorscreen.dialogs.TaskNoteDialog
import app.seven.jotter.core.models.TaskCategory
import app.seven.jotter.core.models.TaskModel
import com.example.inventory.ui.navigation.NavigationDestination

object TaskEditorDestination : NavigationDestination {
    override val route = "task_editor"
    override val titleRes = R.string.task_editor_screen_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorScreen(
    taskEditorViewModel: TaskEditorViewModel = hiltViewModel<TaskEditorViewModel>(),
    navigateBack: () -> Unit,
) {
    val showDialogEvent = rememberSaveable {
        mutableStateOf<TaskEditorUIEvents.ShowDialog<*>?>(null)
    }
    val isKeyboardOpen by keyboardAsState()
    val keyboard = LocalSoftwareKeyboardController.current

    val task = taskEditorViewModel.task.value

    ObserveFlowStateAsEvents(flow = taskEditorViewModel.uiNavigationEvent) { event ->
        when (event) {
            is TaskEditorUIEvents.ShowDialog<*> -> {
                showDialogEvent.value = if (event.dialogType == DialogType.NONE)
                    null
                else
                    event
            }
        }
    }

    fun updateTask(taskModel: TaskModel) {
        with(taskEditorViewModel) {
            onAction(TaskEditorUIActions.UpdateTask(taskModel))
            onAction(TaskEditorUIActions.CloseDialog)
        }
    }

    fun closeDialog() {
        taskEditorViewModel.onAction(TaskEditorUIActions.CloseDialog)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "New Task",
                )
            })
        },

        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        if (showDialogEvent.value != null) {
            when (showDialogEvent.value!!.dialogType) {
                DialogType.CATEGORY -> {
                    TaskCategoryDialog(
                        categories = TaskCategory.entries,
                        onCancel = ::closeDialog,
                        onChangeCategory = { updateTask(task.copy(category = it)) }
                    )
                }

                DialogType.DATE -> {
                    CustomDatePikerDialog(
                        onDateSelected = { updateTask(task.copy(date = it)) },
                        onDismiss = ::closeDialog
                    )
                }

                DialogType.TIME_REMINDER -> {
                    EditTaskReminder(
                        taskReminders = taskEditorViewModel.task.value.reminders,
                        onSave = {
                            updateTask(task.copy(reminders = it))
                        },
                        onDismiss = ::closeDialog
                    )
                }

                DialogType.NOTE -> {
                    TaskNoteDialog(
                        taskNote = task.note,
                        onSave = {
                            updateTask(task.copy(note = it))
                        },
                        onCancel = ::closeDialog
                    )
                }

                DialogType.CHECKLIST -> {
                    TaskCheckListDialog(
                        list = task.checkList,
                        onSave = {
                            updateTask(task.copy(checkList = it))
                        },
                        onCancel = ::closeDialog
                    )
                }

                else -> Unit
            }
        }

        TaskEditor(
            modifier = Modifier.padding(innerPadding),
            taskModel = task,
            onCancel = {
                if (isKeyboardOpen) {
                    keyboard?.hide()
                } else {
                    navigateBack()
                }
            },
            onAction = taskEditorViewModel::onAction
        )
    }
}