package app.seven.jotter.app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.seven.jotter.app.theme.spacing
import app.seven.jotter.app.theme.textSize

@Composable
fun ActionEditorDialog(
    modifier: Modifier = Modifier,
    title: String,
    confirmLabel: String = "SAVE",
    cancelLabel: String = "CANCEL",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {

    CustomDialog(
        modifier = modifier,
        onCancel = onCancel,
    ) {
        Column{
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                fontSize = textSize().medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing().medium)
            )
            HorizontalDivider(thickness = .3.dp)
            Spacer(modifier = Modifier.size(spacing().xSmall))
            content()
            ConfirmCancelButton(
                confirmLabel = confirmLabel,
                cancelLabel = cancelLabel,
                onConfirm = onConfirm, onCancel = onCancel
            )
        }

    }
}


@Preview
@Composable
fun TaskEditorDialogPreview() {
    ActionEditorDialog(
        title = "Dialog",
        onCancel = { /*TODO*/ },
        onConfirm = { /*TODO*/ }
    ) {
        Box(modifier = Modifier.padding(8.dp))
    }
}