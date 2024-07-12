package app.seven.jotter.app.screens.taskeditorscreen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import app.seven.jotter.app.components.CustomDatePikerDialog
import app.seven.jotter.app.components.CustomTimePikerDialog
import app.seven.jotter.app.components.ObserveFlowStateAsEvents
import app.seven.jotter.app.components.keyboardAsState
import app.seven.jotter.app.screens.taskeditorscreen.dialogs.EditReminderDialog
import app.seven.jotter.app.screens.taskeditorscreen.dialogs.SelectCategoryDialog
import app.seven.jotter.app.screens.taskeditorscreen.dialogs.TimeAndReminderDialog
import app.seven.jotter.app.theme.JotterTheme
import app.seven.jotter.core.models.TaskCategory
import app.seven.jotter.core.models.TaskModel
import app.seven.jotter.core.models.TaskReminder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskEditorActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            JotterTheme {
                val taskEditorViewModel = hiltViewModel<TaskEditorViewModel>()
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
                                SelectCategoryDialog(
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
                                val taskReminderAction = showDialogEvent.value!!.data

                                if (taskReminderAction != null && taskReminderAction is TaskReminderAction && taskReminderAction.data != null) {
                                    if (taskReminderAction.action == TimeReminderActionType.SET_TIME) {
                                        CustomTimePikerDialog(
                                            onTimeSelected = {
                                                with(taskEditorViewModel) {
                                                    onAction(TaskEditorUIActions.CloseDialog)
                                                    onAction(TaskEditorUIActions.EditReminder(taskReminderAction.data.copy(time = it)))
                                                }
                                            },
                                            onDismiss = {
                                                with(taskEditorViewModel) {
                                                    onAction(TaskEditorUIActions.CloseDialog)
                                                    onAction(TaskEditorUIActions.EditReminder(taskReminderAction.data))
                                                }
                                            }
                                        )
                                    } else {
                                        EditReminderDialog(
                                            taskReminder = taskReminderAction.data,
                                            onCancel = {
                                                with(taskEditorViewModel) {
                                                    onAction(TaskEditorUIActions.CloseDialog)
                                                    onAction(TaskEditorUIActions.EditReminder(null))
                                                }
                                            },
                                            onEditTime = {
                                                with(taskEditorViewModel) {
                                                    onAction(TaskEditorUIActions.CloseDialog)
                                                    onAction(TaskEditorUIActions.SetReminderTime(it))
                                                }
                                            },
                                            onUpdate = {
                                                val updatedTask = task.copy(
                                                    reminders = task.reminders
                                                        .toMutableSet().apply { add(it) }
                                                        .toList()
                                                )
                                                updateTask(updatedTask)
                                                with(taskEditorViewModel) {
                                                    onAction(TaskEditorUIActions.CloseDialog)
                                                    onAction(TaskEditorUIActions.EditReminder(null))
                                                }
                                            }
                                        )
                                    }

                                } else {
                                    TimeAndReminderDialog(
                                        task = task,
                                        editReminder = {
                                            with(taskEditorViewModel) {
                                                onAction(TaskEditorUIActions.CloseDialog)
                                                onAction(TaskEditorUIActions.EditReminder(it))
                                            }
                                        },
                                        onUpdateTimeAndReminders = {
                                            updateTask(task.copy(reminders = it))
                                        },
                                        onCancel = ::closeDialog
                                    )
                                }
                            }

                            DialogType.PRIORITY -> TODO()
                            DialogType.NOTE -> TODO()
                            DialogType.CHECKLIST -> TODO()
                            DialogType.NONE -> Unit
                        }
                    }

                    TaskEditor(
                        modifier = Modifier.padding(innerPadding),
                        taskModel = task,
                        onCancel = {
                            if (isKeyboardOpen) {
                                keyboard?.hide()
                            } else {
                                finish()
                            }
                        },
                        onAction = taskEditorViewModel::onAction
                    )
                }
            }
        }
    }
}
/*

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MinimalTheme {
        Greeting("Android")
    }
}*/
