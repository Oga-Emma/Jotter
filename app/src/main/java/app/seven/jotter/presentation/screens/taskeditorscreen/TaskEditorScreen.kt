package app.seven.jotter.presentation.screens.taskeditorscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import app.seven.jotter.R
import app.seven.jotter.presentation.components.CustomDatePikerDialog
import app.seven.jotter.presentation.components.ObserveFlowStateAsEvents
import app.seven.jotter.presentation.components.keyboardAsState
import app.seven.jotter.presentation.screens.JotterTopAppBar
import app.seven.jotter.presentation.screens.mainscreen.appscaffold.viewmodel.AppNavigation
import app.seven.jotter.presentation.screens.taskeditorscreen.component.EditTaskReminder
import app.seven.jotter.presentation.screens.taskeditorscreen.dialogs.TaskCategoryDialog
import app.seven.jotter.presentation.screens.taskeditorscreen.dialogs.TaskCheckListDialog
import app.seven.jotter.presentation.screens.taskeditorscreen.dialogs.TaskNoteDialog
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
    onNavigate: (AppNavigation) -> Unit
) {
    var showDialogEvent by rememberSaveable {
        mutableStateOf<TaskEditorUIEvents.ShowDialog<*>?>(null)
    }
    val isKeyboardOpen by keyboardAsState()
    val keyboard = LocalSoftwareKeyboardController.current

    val task = taskEditorViewModel.task

    ObserveFlowStateAsEvents(flow = taskEditorViewModel.uiNavigationEvent) { event ->
        when (event) {
            is TaskEditorUIEvents.ShowDialog<*> -> {
                showDialogEvent = if (event.dialogType == DialogType.NONE)
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
            JotterTopAppBar(
                title = "Task Editor",
                canNavigateBack = true,
                centerAligned = true,
                navigateUp = { onNavigate(AppNavigation.NavigateBack) }
            )
        },

        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        if (showDialogEvent != null) {
            when (showDialogEvent!!.dialogType) {
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
                        taskReminders = taskEditorViewModel.task.reminders,
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
                    onNavigate(AppNavigation.NavigateBack)
                }
            },
            onAction = taskEditorViewModel::onAction
        )
    }
}