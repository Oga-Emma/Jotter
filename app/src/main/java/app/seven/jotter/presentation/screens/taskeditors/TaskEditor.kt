package app.seven.jotter.presentation.screens.taskeditors

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
import androidx.compose.material.icons.automirrored.outlined.StickyNote2
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.NotificationAdd
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.seven.jotter.presentation.components.CircularAvatar
import app.seven.jotter.presentation.components.CircularIcon
import app.seven.jotter.presentation.components.ConfirmCancelButton
import app.seven.jotter.presentation.components.CustomDivider
import app.seven.jotter.presentation.screens.taskeditors.component.IconHelper
import app.seven.jotter.presentation.theme.pallet
import app.seven.jotter.presentation.theme.spacing
import app.seven.jotter.common.extensions.formatDate
import app.seven.jotter.common.extensions.titleCase
import app.seven.jotter.core.models.TaskModel
import app.seven.jotter.core.models.TaskModelCreator

@Composable
fun TaskEditor(
    modifier: Modifier = Modifier,
    taskModel: TaskModel,
    onCancel: () -> Unit,
    onAction: (TaskEditorUIActions) -> Unit,
    onShowMessage: (String) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    var isValid by remember { mutableStateOf(true) }
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
            isError = !isValid,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() })
        )

        if (!isValid) {
            Text(
                modifier = Modifier
                    .padding(horizontal = spacing().medium),
                text = "Please enter a task", color = Color.Red)
        }

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
                    CircularAvatar{
                        Text("${taskModel.reminders.size}", color = pallet().primary)
                    }
                },
                onClick = {
                    onAction(TaskEditorUIActions.EditReminder)
                }
            )
//            TaskEditorOption(
//                title = "Priority",
//                icon = Icons.Rounded.LowPriority,
//                leading = {
//                    Text(taskModel.priority.name.titleCase(), color = pallet().primary)
//                },
//                onClick = {
//                    onAction(TaskEditorUIActions.SelectPriority)
//                }
//            )
            TaskEditorOption(
                title = "Note",
                icon = Icons.AutoMirrored.Rounded.Notes,
                leading = {
                    CircularIcon(
                        imageVector = if (taskModel.note.isBlank())
                            Icons.AutoMirrored.Outlined.StickyNote2
                        else Icons.Outlined.EditNote,
                        contentDescription = "Note"
                    )
                },
                onClick = {
                    onAction(TaskEditorUIActions.EditNote)
                }
            )
            TaskEditorOption(
                title = "Checklist",
                icon = Icons.Rounded.Checklist,
                leading = {
                    CircularAvatar {
                        Text(taskModel.checkList.count().toString(), color = pallet().primary)
                    }
                },
                onClick = {
                    onAction(TaskEditorUIActions.EditCheckList)
                }
            )
            CustomDivider()
        }
        Spacer(
            modifier = Modifier
                .padding(vertical = spacing().small)
        )
        ConfirmCancelButton(onConfirm = {
            if(description.isBlank()){
                isValid = false
                onShowMessage("Please enter a valid description")
                return@ConfirmCancelButton
            } else {
                isValid = true
            }

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
            },
            onShowMessage = {}
        )
    }
}