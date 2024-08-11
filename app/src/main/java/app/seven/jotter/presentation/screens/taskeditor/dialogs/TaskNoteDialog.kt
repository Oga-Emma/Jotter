package app.seven.jotter.presentation.screens.taskeditor.dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import app.seven.jotter.presentation.components.ActionEditorDialog
import app.seven.jotter.presentation.theme.spacing

@Composable
fun TaskNoteDialog(
    taskNote: String,
    onSave: (String) -> Unit,
    onCancel: () -> Unit,
) {
    val note = rememberSaveable { mutableStateOf(taskNote) }

    ActionEditorDialog(
        modifier = Modifier
            .imePadding(),
        title = "Note",
        onCancel = onCancel,
        onConfirm = {
            onSave(note.value)
        }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray
            ),
            value = note.value,
            onValueChange = { note.value = it },
            minLines = 5,

            )
    }
}


@Preview
@Composable
fun TaskNoteDialogPreview() {
    TaskNoteDialog(
        taskNote = "",
        onCancel = { /*TODO*/ },
        onSave = {}
    )
}