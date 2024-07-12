package app.seven.jotter.app.screens.taskeditorscreen.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import app.seven.jotter.app.components.CustomTimePikerDialog
import app.seven.jotter.app.screens.taskeditorscreen.dialogs.EditReminderDialog
import app.seven.jotter.app.screens.taskeditorscreen.dialogs.TimeAndReminderDialog
import app.seven.jotter.core.models.TaskReminder

@Composable
fun EditTaskReminder(
    taskReminders: List<TaskReminder>,
    onSave: (List<TaskReminder>) -> Unit,
    onDismiss: () -> Unit
) {

    val reminders = rememberSaveable { mutableStateOf(taskReminders) }
    val selectedTaskReminderAction = remember { mutableStateOf<EditTaskReminderAction?>(null) }


    fun showDialog(which: EditTaskDialogType){

    }

    if (selectedTaskReminderAction.value != null) {
        val selectedReminder = selectedTaskReminderAction.value!!

        if (selectedReminder.action == EditTaskReminderActionType.EDIT_REMINDER_TIME) {
            CustomTimePikerDialog(
                onTimeSelected = {
                    selectedTaskReminderAction.value = selectedReminder.copy(
                        reminder = selectedReminder.reminder.copy(time = it),
                        action = EditTaskReminderActionType.DEFAULT
                    )
                },
                onDismiss = {
                    selectedTaskReminderAction.value = selectedReminder.copy(
                        action = EditTaskReminderActionType.DEFAULT
                    )
                }
            )
        } else {
            EditReminderDialog(
                taskReminder = selectedReminder.reminder,
                onCancel = {
                    selectedTaskReminderAction.value = null
                },
                onEditTime = {
                    selectedTaskReminderAction.value = EditTaskReminderAction(
                        reminder = it,
                        action = EditTaskReminderActionType.EDIT_REMINDER_TIME
                    )
                },
                onUpdate = { reminder ->
                    reminders.value = reminders.value.toMutableList().apply {
                        val currentIndex = this.indexOfFirst { it.id == reminder.id }

                        if (currentIndex == -1) {
                            this.add(reminder)
                        } else {
                            this[currentIndex] = reminder
                        }
                    }

                    selectedTaskReminderAction.value = null
                }
            )
        }
    } else {
        TimeAndReminderDialog(
            taskReminder = reminders.value,
            editReminder = {
                selectedTaskReminderAction.value = EditTaskReminderAction(
                    reminder = it,
                    action = EditTaskReminderActionType.DEFAULT
                )
            },
            onSave = { onSave(it) },
            onCancel = { onDismiss() }
        )
    }
}

enum class EditTaskDialogType {
    MAIN_DIALOG, EDI
}

enum class EditTaskReminderActionType {
    DEFAULT, EDIT_REMINDER_TIME
}

data class EditTaskReminderAction(
    val reminder: TaskReminder,
    val action: EditTaskReminderActionType
)
