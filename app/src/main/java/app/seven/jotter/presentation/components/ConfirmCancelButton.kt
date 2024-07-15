package app.seven.jotter.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.seven.jotter.presentation.theme.spacing

@Composable
fun ConfirmCancelButton(
    confirmLabel: String = "SAVE",
    cancelLabel: String = "CANCEL",
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    Column {
        HorizontalDivider(
            thickness = .3.dp
        )
//        Spacer(modifier = Modifier.padding(vertical = spacing().small))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.tertiary,
                ),
                onClick = onCancel
            ) {
                Text(cancelLabel.uppercase())
            }

            VerticalDivider(
                modifier = Modifier.height(MaterialTheme.spacing.large),
                color = MaterialTheme.colorScheme.secondary,
                thickness = .3.dp
            )

            TextButton(
                modifier = Modifier.weight(1f),
                onClick = onConfirm
            ) {
                Text(confirmLabel.uppercase())
            }
        }
    }
}

@Preview
@Composable
fun ConfirmCancelButtonPreview() {
    ConfirmCancelButton(
        confirmLabel = "CONFIRM",
        onConfirm = {},
        onCancel = {},
    )
}