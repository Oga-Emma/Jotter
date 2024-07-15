package app.seven.jotter.presentation.screens.taskeditorscreen.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.AlarmOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import app.seven.jotter.presentation.components.ActionEditorDialog
import app.seven.jotter.presentation.components.CircularIcon
import app.seven.jotter.presentation.components.CustomDivider
import app.seven.jotter.presentation.components.EmptyState
import app.seven.jotter.presentation.theme.spacing
import app.seven.jotter.presentation.theme.textSize
import app.seven.jotter.common.extensions.formatTime
import app.seven.jotter.core.models.TaskReminder
import app.seven.jotter.core.models.TaskReminderType
import java.time.LocalTime
import java.util.UUID

@Composable
fun TaskTimeAndReminderDialog(
    reminders: List<TaskReminder>,
    onCancel: () -> Unit,
    onEditReminder: (TaskReminder) -> Unit,
    onDeleteReminder: (TaskReminder) -> Unit,
    onSave: (List<TaskReminder>) -> Unit,
) {
    ActionEditorDialog(
        title = "TIME AND REMINDER",
        onCancel = onCancel,
        onConfirm = {
            onSave(reminders)
        }) {
        Column {

            if (reminders.isEmpty()) {
                EmptyState(
                    modifier = Modifier.padding(spacing().medium),
                    icon = Icons.Default.AlarmOff,
                    label = "No reminders for this event",
                )
            } else {
                LazyColumn {
                    items(reminders.size) { index ->
                        val reminder = reminders[index]
                        ReminderItem(
                            taskReminder = reminder,
                            onEdit = {
                                onEditReminder(it)
                            },
                            onDelete = { onDeleteReminder(it) },
                        )
                    }
                }
            }
            CustomDivider()
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    TaskReminder(
                        id = UUID.randomUUID(),
                        type = TaskReminderType.NOTIFICATION,
                        time = LocalTime.of(12, 0)
                    ).also {
                        onEditReminder(it)
                    }
                }) {
                Icon(Icons.Rounded.AddCircleOutline, "")
                Spacer(modifier = Modifier.size(spacing().small))
                Text(text = "NEW REMINDER")
            }
        }
    }
}

@Composable
fun EditReminderDialog(
    taskReminder: TaskReminder,
    onCancel: () -> Unit,
    onEditTime: (TaskReminder) -> Unit,
    onUpdate: (TaskReminder) -> Unit,
) {
    val reminder = remember { mutableStateOf(taskReminder) }

    ActionEditorDialog(
        title = "REMINDER",
        confirmLabel = "Confirm",
        onCancel = onCancel,
        onConfirm = {
            onUpdate(reminder.value)
        }) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing().lSmall)
                    .clickable {
                        onEditTime(reminder.value)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = reminder.value.time.formatTime(),
                    fontSize = textSize().lMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Reminder time")
            }
            CustomDivider()
            ReminderTypeArea(
                selectedReminderType = reminder.value.type,
                onSelectReminder = {
                    reminder.value = reminder.value.copy(type = it)
                }
            )
        }
    }
}

@Composable
fun ReminderTypeArea(
    selectedReminderType: TaskReminderType,
    onSelectReminder: (TaskReminderType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing().lSmall),
    ) {
        Text(text = "Reminder type")
        Spacer(modifier = Modifier.height(spacing().small))
        Row {
            ReminderTypeButton(
                modifier = Modifier
                    .weight(1f)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = spacing().small,
                            bottomStart = spacing().small
                        )
                    )
                    .clickable {
                        onSelectReminder(TaskReminderType.NONE)
                    },
                icon = Icons.Outlined.NotificationsOff,
                label = "Don't Remind",
                isSelected = selectedReminderType == TaskReminderType.NONE,
            )
            ReminderTypeButton(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onSelectReminder(TaskReminderType.NOTIFICATION)
                    },
                icon = Icons.Outlined.Notifications,
                label = "Notification",
                isSelected = selectedReminderType == TaskReminderType.NOTIFICATION,
            )
            ReminderTypeButton(
                modifier = Modifier
                    .weight(1f)
                    .clip(
                        shape = RoundedCornerShape(
                            topEnd = spacing().small,
                            bottomEnd = spacing().small
                        )
                    )
                    .clickable {
                        onSelectReminder(TaskReminderType.ALARM)
                    },
                icon = Icons.Outlined.Alarm,
                label = "Alarm",
                isSelected = selectedReminderType == TaskReminderType.ALARM,
            )
        }
    }
}

@Composable
fun ReminderTypeButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    isSelected: Boolean
) {

    val color = if (isSelected) MaterialTheme.colorScheme.primary else Color.DarkGray
    Column(
        modifier = modifier
            .background(color.copy(alpha = .2f))
            .padding(spacing().xSmall),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = color)
        Spacer(modifier = Modifier.height(spacing().xSmall))
        Text(text = label, fontSize = textSize().lSmall, color = color)
    }
}

@Composable
fun ReminderItem(
    taskReminder: TaskReminder,
    onEdit: (TaskReminder) -> Unit,
    onDelete: (TaskReminder) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.padding(spacing().small)
        ) {
            CircularIcon(
                modifier = Modifier.clickable {
                    onEdit(taskReminder)
                },
                imageVector = when(taskReminder.type){
                    TaskReminderType.NONE ->   Icons.Outlined.NotificationsOff
                    TaskReminderType.ALARM ->   Icons.Outlined.AlarmOn
                    TaskReminderType.NOTIFICATION ->   Icons.Outlined.NotificationsActive
                },
                contentDescription = "Notification Icon"
            )
            Spacer(modifier = Modifier.width(spacing().small))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onEdit(taskReminder)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = taskReminder.time.formatTime(),
                    fontSize = textSize().medium
                )
                Text(text = "Reminder time", fontSize = textSize().xMedium)
            }
            Spacer(modifier = Modifier.width(spacing().small))
            CircularIcon(
                modifier = Modifier.clickable {
                    onDelete(taskReminder)
                },
                imageVector = Icons.Default.DeleteOutline,
                contentDescription = "Notification Icon",
                backgroundColor = Color.DarkGray
            )
        }
        CustomDivider()
    }
}

@Preview
@Composable
fun TimeAndReminderDialogPreview() {
    TaskTimeAndReminderDialog(
        reminders = listOf(
            TaskReminder(
                id = UUID.randomUUID(),
                type = TaskReminderType.NOTIFICATION,
                time = LocalTime.of(12, 0)
            )
        ),
        onCancel = { /*TODO*/ }, onEditReminder = {}, onDeleteReminder = {}) {
    }
}

@Preview
@Composable
fun EditReminderDialogPreview() {
    EditReminderDialog(
        taskReminder = TaskReminder(
            id = UUID.randomUUID(),
            type = TaskReminderType.NOTIFICATION,
            time = LocalTime.of(12, 0)
        ),
        onEditTime = {},
        onCancel = { /*TODO*/ }) {
    }
}

@Preview
@Composable
fun ReminderItemPreview() {
    ReminderItem(
        taskReminder = TaskReminder(
            id = UUID.randomUUID(),
            type = TaskReminderType.NOTIFICATION,
            time = LocalTime.of(12, 0)
        ),
        onEdit = {},
        onDelete = {}
    )
}