package app.seven.jotter.presentation.screens.taskeditors

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import app.seven.jotter.R
import app.seven.jotter.presentation.components.CustomDatePikerDialog
import app.seven.jotter.presentation.helpers.ObserveFlowStateAsEvents
import app.seven.jotter.presentation.components.keyboardAsState
import app.seven.jotter.presentation.screens.JotterTopAppBar
import app.seven.jotter.presentation.screens.taskeditors.component.EditTaskReminder
import app.seven.jotter.presentation.screens.taskeditors.dialogs.TaskCategoryDialog
import app.seven.jotter.presentation.screens.taskeditors.dialogs.TaskCheckListDialog
import app.seven.jotter.presentation.screens.taskeditors.dialogs.TaskNoteDialog
import app.seven.jotter.core.models.TaskCategory
import app.seven.jotter.core.models.TaskModel
import app.seven.jotter.presentation.helpers.NavigationDestination
import app.seven.jotter.presentation.screens.appscaffold.appscaffold.viewmodel.AppNavigationAction
import app.seven.jotter.presentation.screens.appscaffold.appscaffold.viewmodel.PopupMessageAction
import kotlin.reflect.KFunction1

object TaskEditorDestination : NavigationDestination {
    override val route = "task_editor"
    override val titleRes = R.string.task_editor_screen_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorScreen(
    viewModel: TaskEditorViewModel = hiltViewModel<TaskEditorViewModel>(),
    onNavigationAction: KFunction1<AppNavigationAction, Unit>,
    onShowPopupMessage: (PopupMessageAction) -> Unit,
) {
    var showDialogEvent by remember { mutableStateOf(DialogType.NONE) }

    val isKeyboardOpen by keyboardAsState()
    val keyboard = LocalSoftwareKeyboardController.current

    val task = viewModel.task

    ObserveFlowStateAsEvents(flow = viewModel.uiNavigationEvent) { event ->
        when (event) {
            is TaskEditorUIEvents.ShowDialog -> {
                showDialogEvent = event.dialogType
            }
        }
    }

    fun updateTask(taskModel: TaskModel) {
        with(viewModel) {
            onAction(TaskEditorUIActions.UpdateTask(taskModel))
            onAction(TaskEditorUIActions.CloseDialog)
        }
    }

    fun closeDialog() {
        viewModel.onAction(TaskEditorUIActions.CloseDialog)
    }

    Scaffold(
        topBar = {
            JotterTopAppBar(
                title = "Task Editor",
                canNavigateBack = true,
                centerAligned = true,
                navigateUp = {
                    onNavigationAction(AppNavigationAction.PreviousScreen)
                }
            )
        },

        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        when (showDialogEvent) {
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
                    taskReminders = viewModel.task.reminders,
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

        TaskEditor(
            modifier = Modifier.padding(innerPadding),
            taskModel = task,
            onCancel = {
                if (isKeyboardOpen) {
                    keyboard?.hide()
                } else {
                    onNavigationAction(AppNavigationAction.PreviousScreen)
                }
            },
            onAction = viewModel::onAction,
            onShowMessage = {
                onShowPopupMessage(
                    PopupMessageAction.Toast(it)
                )
            }
        )
    }
}