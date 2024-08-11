package app.seven.jotter.presentation.screens.taskeditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskEditorActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
//            JotterTheme {
//                val taskEditorViewModel = hiltViewModel<TaskEditorViewModel>()
//                val showDialogEvent = rememberSaveable {
//                    mutableStateOf<TaskEditorUIEvents.ShowDialog<*>?>(null)
//                }
//                val isKeyboardOpen by keyboardAsState()
//                val keyboard = LocalSoftwareKeyboardController.current
//
//                val task = taskEditorViewModel.task
//
//                ObserveFlowStateAsEvents(flow = taskEditorViewModel.uiNavigationEvent) { event ->
//                    when (event) {
//                        is TaskEditorUIEvents.ShowDialog<*> -> {
//                            showDialogEvent.value = if (event.dialogType == DialogType.NONE)
//                                null
//                            else
//                                event
//                        }
//                    }
//                }
//
//                fun updateTask(taskModel: TaskModel) {
//                    with(taskEditorViewModel) {
//                        onAction(TaskEditorUIActions.UpdateTask(taskModel))
//                        onAction(TaskEditorUIActions.CloseDialog)
//                    }
//                }
//
//                fun closeDialog() {
//                    taskEditorViewModel.onAction(TaskEditorUIActions.CloseDialog)
//                }
//
//                Scaffold(
//                    topBar = {
//                        TopAppBar(title = {
//                            Text(
//                                text = "New Task",
//                            )
//                        })
//                    },
//
//                    modifier = Modifier.fillMaxSize()
//                ) { innerPadding ->
//
//                    if (showDialogEvent.value != null) {
//                        when (showDialogEvent.value!!.dialogType) {
//                            DialogType.CATEGORY -> {
//                                TaskCategoryDialog(
//                                    categories = TaskCategory.entries,
//                                    onCancel = ::closeDialog,
//                                    onChangeCategory = { updateTask(task.copy(category = it)) }
//                                )
//                            }
//
//                            DialogType.DATE -> {
//                                CustomDatePikerDialog(
//                                    onDateSelected = { updateTask(task.copy(date = it)) },
//                                    onDismiss = ::closeDialog
//                                )
//                            }
//
//                            DialogType.TIME_REMINDER -> {
//                                EditTaskReminder(
//                                    taskReminders = taskEditorViewModel.task.reminders,
//                                    onSave = {
//                                        updateTask(task.copy(reminders = it))
//                                    },
//                                    onDismiss = ::closeDialog
//                                )
//                            }
//
//                            DialogType.NOTE -> {
//                                TaskNoteDialog(
//                                    taskNote = task.note,
//                                    onSave = {
//                                        updateTask(task.copy(note = it))
//                                    },
//                                    onCancel = ::closeDialog
//                                )
//                            }
//
//                            DialogType.CHECKLIST -> {
//                                TaskCheckListDialog(
//                                    list = task.checkList,
//                                    onSave = {
//                                        updateTask(task.copy(checkList = it))
//                                    },
//                                    onCancel = ::closeDialog
//                                )
//                            }
//
//                            else -> Unit
//                        }
//                    }
//
//                    TaskEditor(
//                        modifier = Modifier.padding(innerPadding),
//                        taskModel = task,
//                        onCancel = {
//                            if (isKeyboardOpen) {
//                                keyboard?.hide()
//                            } else {
//                                finish()
//                            }
//                        },
//                        onAction = taskEditorViewModel::onAction
//                    )
//                }
//            }
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
