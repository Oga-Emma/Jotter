package app.seven.jotter.app.screens.taskeditorscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.LowPriority
import androidx.compose.material.icons.rounded.NotificationAdd
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.seven.jotter.app.components.ConfirmCancelButton
import app.seven.jotter.app.screens.taskeditorscreen.component.IconHelper
import app.seven.jotter.app.theme.pallet
import app.seven.jotter.app.theme.spacing
import app.seven.jotter.core.extensions.formatDate
import app.seven.jotter.core.extensions.titleCase
import app.seven.jotter.core.models.TaskModel
import app.seven.jotter.core.models.TaskModelCreator

@Composable
fun TaskEditor(
    modifier: Modifier = Modifier,
    taskModel: TaskModel,
    onCancel: () -> Unit,
    onAction: (TaskEditorUIActions) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    var description by remember { mutableStateOf(taskModel.description) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing().medium),
            value = description,
            onValueChange = { description = it },
            label = { Text("Task") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()})
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            TaskEditorOption(
                title = "Category",
                icon = Icons.Default.Category,
                leading = {
                    Text(taskModel.category.name.titleCase())
                    Spacer(modifier = Modifier.width(spacing().small))
                    IconHelper.GetCategoryIcon(taskModel.category)
                },
                onClick = {
                    onAction(TaskEditorUIActions.SelectCategory)
                }
            )
            TaskEditorOption(
                title = "Date",
                icon = Icons.Rounded.DateRange,
                leading = {
                    Text(taskModel.date.formatDate())
                },
                onClick = {
                    onAction(TaskEditorUIActions.SelectDate)
                }
            )
            TaskEditorOption(
                title = "Time and Reminders",
                icon = Icons.Rounded.NotificationAdd,
                leading = {
                    Text("${taskModel.reminders.size}", color = pallet().primary)
                },
                onClick = {
                    onAction(TaskEditorUIActions.EditReminder)
                }
            )
            TaskEditorOption(
                title = "Priority",
                icon = Icons.Rounded.LowPriority,
                leading = {
                    Text(taskModel.priority.name.titleCase(), color = pallet().primary)
                },
                onClick = {
                    onAction(TaskEditorUIActions.SelectPriority)
                }
            )
            TaskEditorOption(
                title = "Note",
                icon = Icons.AutoMirrored.Rounded.Notes,
                leading = {
                },
                onClick = {
                    onAction(TaskEditorUIActions.EditNote)
                }
            )
            TaskEditorOption(
                title = "Checklist",
                icon = Icons.Rounded.Checklist,
                leading = {
                    Text("0", color = pallet().primary)
                },
                onClick = {
                    onAction(TaskEditorUIActions.EditCheckList)
                }
            )

        }
        Spacer(
            modifier = Modifier
                .padding(vertical = spacing().small)
        )
        ConfirmCancelButton(onConfirm = {
            onAction(
                TaskEditorUIActions.SaveTask(
                    taskModel.copy(description = description)
                )
            )
        }, onCancel = onCancel)
    }
}

@Composable
fun TaskEditorOption(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    leading: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onClick()
            }
    ) {
        HorizontalDivider(
            thickness = .3.dp
        )
        Spacer(modifier = Modifier.padding(vertical = spacing().lSmall))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = spacing().medium)
        ) {
            Icon(icon, contentDescription = title, tint = pallet().primary)
            Spacer(modifier = Modifier.padding(horizontal = spacing().small))
            Text(title, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.padding(horizontal = spacing().small))
            leading()
            Icon(
                Icons.Default.ChevronRight,
                "",
                modifier = Modifier.size(spacing().lMedium),
                tint = pallet().primary
            )
        }
        Spacer(modifier = Modifier.size(spacing().lMedium))
    }
}

@Preview
@Composable
fun TaskEditorPreview() {
    Box(
        modifier = Modifier.background(Color.White)
    ) {
        TaskEditor(
            taskModel = TaskModelCreator.newTaskWithDefaultValues(),
            onCancel = {},
            onAction = {

            }
        )
    }
}