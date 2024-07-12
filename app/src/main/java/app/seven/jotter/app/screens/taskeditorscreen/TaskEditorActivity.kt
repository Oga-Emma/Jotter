package app.seven.jotter.app.screens.taskeditorscreen

import android.os.Bundle
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
import app.seven.jotter.app.components.ObserveFlowStateAsEvents
import app.seven.jotter.app.components.keyboardAsState
import app.seven.jotter.app.screens.taskeditorscreen.component.EditTaskReminder
import app.seven.jotter.app.screens.taskeditorscreen.dialogs.NoteDialog
import app.seven.jotter.app.screens.taskeditorscreen.dialogs.SelectCategoryDialog
import app.seven.jotter.app.theme.JotterTheme
import app.seven.jotter.core.models.TaskCategory
import app.seven.jotter.core.models.TaskModel
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
                                EditTaskReminder(
                                    taskReminders = taskEditorViewModel.task.value.reminders,
                                    onSave = {
                                        updateTask(task.copy(reminders = it))
                                    },
                                    onDismiss = {
                                        taskEditorViewModel.onAction(TaskEditorUIActions.CloseDialog)
                                    })
                            }

                            DialogType.NOTE -> {
                                NoteDialog(
                                    taskNote = task.note,
                                    onSave = {
                                        updateTask(task.copy(note = it))
                                    },
                                    onCancel = {
                                        taskEditorViewModel.onAction(TaskEditorUIActions.CloseDialog)
                                    }
                                )
                            }

                            DialogType.CHECKLIST -> TODO()
                            DialogType.PRIORITY -> TODO()
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
