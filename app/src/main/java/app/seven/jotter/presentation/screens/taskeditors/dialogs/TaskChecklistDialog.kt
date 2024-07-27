package app.seven.jotter.presentation.screens.taskeditors.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import app.seven.jotter.presentation.components.ActionEditorDialog
import app.seven.jotter.presentation.components.CircularIcon
import app.seven.jotter.presentation.components.CustomDivider
import app.seven.jotter.presentation.components.EmptyState
import app.seven.jotter.presentation.theme.spacing
import app.seven.jotter.core.models.TaskCheckListItem
import java.util.UUID

@Composable
fun TaskCheckListDialog(
    list: List<TaskCheckListItem>,
    onSave: (List<TaskCheckListItem>) -> Unit,
    onCancel: () -> Unit,
) {
    val checkList = rememberSaveable { mutableStateOf(list) }
    val itemToEdit = rememberSaveable { mutableStateOf<TaskCheckListItem?>(null) }

    fun saveCheckListItem(taskCheckListItem: TaskCheckListItem? = null) {
        if (taskCheckListItem != null) {
            checkList.value = checkList.value.toMutableList().apply {
                val index = indexOfFirst { it.id == taskCheckListItem.id }
                if(index != -1){
                    set(index, taskCheckListItem)
                }else {
                    add(taskCheckListItem)
                }
            }
        }

        itemToEdit.value = null
    }

    fun editCheckListItem(taskCheckListItem: TaskCheckListItem) {
        itemToEdit.value = taskCheckListItem
    }

    fun deleteCheckListItem(taskCheckListItem: TaskCheckListItem) {
        checkList.value = checkList.value.toMutableList().apply {
            remove(taskCheckListItem)
        }
    }

    if (itemToEdit.value != null) {
        EditTaskCheckListItemDialog(
            item = itemToEdit.value!!,
            onSave = ::saveCheckListItem,
            onCancel = ::saveCheckListItem
        )
    }

    ActionEditorDialog(
        modifier = Modifier
            .imePadding(),
        title = "Note",
        onCancel = onCancel,
        onConfirm = { onSave(checkList.value) }
    ) {
        Column{
            if (checkList.value.isEmpty()) {
                EmptyState(
                    modifier = Modifier.padding(spacing().medium),
                    icon = Icons.Default.Checklist,
                    label = "No item added",
                )
            } else {
                LazyColumn {
                    items(checkList.value.size) { index ->
                        TaskCheckListDialogItem(
                            item = checkList.value[index],
                            onEdit = ::editCheckListItem,
                            onDelete = ::deleteCheckListItem,
                        )
                    }
                }
            }

            CustomDivider()
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    TaskCheckListItem(
                        id = UUID.randomUUID(),
                        description = "",
                        isDone = false
                    ).also {
                        editCheckListItem(it)
                    }
                }) {
                Icon(Icons.Rounded.AddCircleOutline, "")
                Spacer(modifier = Modifier.size(spacing().small))
                Text(text = "NEW LIST ITEM")
            }
        }
    }
}

@Composable
fun EditTaskCheckListItemDialog(
    item: TaskCheckListItem,
    onSave: (TaskCheckListItem) -> Unit,
    onCancel: () -> Unit,
) {
    val text = rememberSaveable { mutableStateOf(item.description) }

    ActionEditorDialog(
        modifier = Modifier
            .imePadding(),
        title = "Note",
        onCancel = onCancel,
        onConfirm = {
            if(text.value.isBlank()){
                onCancel()
            }else{
                onSave(item.copy(description = text.value))
            }
        }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing().small),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray
            ),
            value = text.value,
            onValueChange = { text.value = it },
            minLines = 2
        )
    }
}

@Composable
fun TaskCheckListDialogItem(
    item: TaskCheckListItem,
    onEdit: (TaskCheckListItem) -> Unit,
    onDelete: (TaskCheckListItem) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.padding(vertical = spacing().small, horizontal = spacing().medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = spacing().medium)
                    .clickable { onEdit(item) },
                text = item.description
            )

            CircularIcon(
                modifier = Modifier.clickable { onDelete(item) },
                imageVector = Icons.Rounded.DeleteOutline,
                backgroundColor = Color.DarkGray,
                contentDescription = "delete item"
            )
        }
        CustomDivider()
    }
}

@Preview
@Composable
fun TaskCheckListDialogPreview1() {
    TaskCheckListDialog(
        list = listOf(),
        onCancel = {},
        onSave = {}
    )
}

@Preview
@Composable
fun TaskCheckListDialogPreview2() {
    TaskCheckListDialog(
        list = listOf(
            TaskCheckListItem(id = UUID.randomUUID(), description = "Something something check list", isDone = false)
        ),
        onCancel = {},
        onSave = {}
    )
}

@Preview
@Composable
fun EditTaskCheckListItemDialogPreview() {
    EditTaskCheckListItemDialog(
        item = TaskCheckListItem(id = UUID.randomUUID(), description = "", isDone = false),
        onCancel = {},
        onSave = {}
    )
}

@Preview
@Composable
fun TaskCheckListDialogItemPreview() {
    TaskCheckListDialogItem(
        item = TaskCheckListItem(id = UUID.randomUUID(), description = "", isDone = false),
        onEdit = {},
        onDelete = {}
    )
}
