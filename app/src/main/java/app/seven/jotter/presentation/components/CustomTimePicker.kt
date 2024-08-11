package app.seven.jotter.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.seven.jotter.presentation.theme.spacing
import java.time.LocalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePikerDialog(
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
) {
    val timePickerState = rememberTimePickerState()

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onTimeSelected(LocalTime.of(timePickerState.hour, timePickerState.minute))
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "CANCEL")
            }
        }
    ) {
        TimePicker(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    spacing.medium
                ),
            state = timePickerState
        )
    }
}

@Preview
@Composable
fun CustomTimePikerDialogPreview() {
    CustomTimePikerDialog(
        onTimeSelected = {},
        onDismiss = {},
    )
}