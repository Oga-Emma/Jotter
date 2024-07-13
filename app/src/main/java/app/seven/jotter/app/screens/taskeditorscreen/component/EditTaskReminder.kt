package app.seven.jotter.app.screens.taskeditorscreen.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import app.seven.jotter.app.components.CustomTimePikerDialog
import app.seven.jotter.app.screens.taskeditorscreen.dialogs.EditReminderDialog
import app.seven.jotter.app.screens.taskeditorscreen.dialogs.TaskTimeAndReminderDialog
import app.seven.jotter.core.models.TaskReminder

@Composable
fun EditTaskReminder(
    taskReminders: List<TaskReminder>,
    onSave: (List<TaskReminder>) -> Unit,
    onDismiss: () -> Unit
) {
    val reminders = rememberSaveable { mutableStateOf(taskReminders) }
    val selectedTaskReminderAction = remember { mutableStateOf<EditTaskReminderAction?>(null) }

    fun showMainDialog(updatedReminders: List<TaskReminder>) {
        reminders.value = updatedReminders
        selectedTaskReminderAction.value = null
    }

    fun showEditReminderDialog(taskReminder: TaskReminder){
        selectedTaskReminderAction.value = EditTaskReminderAction(
            reminder = taskReminder,
            action = EditTaskReminderActionType.DEFAULT
        )
    }

    fun showEditReminderTimeDialog(taskReminder: TaskReminder){
        selectedTaskReminderAction.value = EditTaskReminderAction(
            reminder = taskReminder,
            action = EditTaskReminderActionType.EDIT_REMINDER_TIME
        )
    }

    if (selectedTaskReminderAction.value != null) {
        val selectedReminder = selectedTaskReminderAction.value!!

        if (selectedReminder.action == EditTaskReminderActionType.EDIT_REMINDER_TIME) {
            CustomTimePikerDialog(
                onTimeSelected = {
                   showEditReminderDialog(selectedReminder.reminder.copy(time = it))
                },
                onDismiss = {
                    showEditReminderDialog(selectedReminder.reminder)
                }
            )
        } else {
            EditReminderDialog(
                taskReminder = selectedReminder.reminder,
                onCancel = { showMainDialog( reminders.value ) },
                onEditTime = { showEditReminderTimeDialog(it) },
                onUpdate = { reminder ->
                    val updatedReminders = reminders.value.toMutableList().apply {
                        val currentIndex = this.indexOfFirst { it.id == reminder.id }

                        if (currentIndex == -1) {
                            this.add(reminder)
                        } else {
                            this[currentIndex] = reminder
                        }
                    }

                    showMainDialog(updatedReminders)
                }
            )
        }
    } else {
        TaskTimeAndReminderDialog(
            reminders = reminders.value,
            onEditReminder = { showEditReminderDialog(it) },
            onDeleteReminder = {
                reminders.value = reminders.value.toMutableList().apply {
                    remove(it)
                }
            },
            onSave = { onSave(it) },
            onCancel = { onDismiss() }
        )
    }
}

enum class EditTaskDialogType {
    MAIN_DIALOG, EDIT_REMINDER, EDIT_REMINDER_TIME
}

enum class EditTaskReminderActionType {
    DEFAULT, EDIT_REMINDER_TIME
}

data class EditTaskReminderAction(
    val reminder: TaskReminder,
    val action: EditTaskReminderActionType
)
