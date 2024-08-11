package app.seven.jotter.presentation.screens.taskdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.LowPriority
import androidx.compose.material.icons.outlined.RepeatOne
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.seven.jotter.common.extensions.titleCase
import app.seven.jotter.core.models.TaskStatus
import app.seven.jotter.presentation.components.CircularAvatar
import app.seven.jotter.presentation.components.ConfirmCancelButton
import app.seven.jotter.presentation.theme.spacing
import app.seven.jotter.presentation.theme.textSize
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsBottomSheet(onClose: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState
    ) {
        TaskDetails(
            closeBottomSheet = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onClose()
                    }
                }
            }
        )
    }
}

@Composable
fun TaskDetails(
    closeBottomSheet: () -> Unit
) {
    Column(
        modifier = Modifier.padding(bottom = spacing.medium)
    ) {
        Text(
            modifier = Modifier.padding(spacing.medium),
            text = "Do some random stuff",
            fontSize = textSize.lMedium
        )
        LabelWithIcon(content = "Apr 5", icon = Icons.Outlined.CalendarToday)
        HorizontalDivider(modifier = Modifier.padding(vertical = spacing.lSmall))
        TaskStatusArea()
        HorizontalDivider(modifier = Modifier.padding(vertical = spacing.lSmall))
        LabelWithIcon(
            heading = "Reminders",
            content = "07:00 AM • O6:00 PM",
            icon = Icons.Outlined.Alarm
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        LabelWithIcon(
            heading = "Priority",
            content = "Default",
            icon = Icons.Outlined.LowPriority
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        LabelWithIcon(
            heading = "Frequency",
            content = "Once",
            icon = Icons.Outlined.RepeatOne
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = spacing.lSmall))
        LabelWithIcon(
            heading = "Notes",
            content = "Add note...",
            icon = Icons.Outlined.EditNote
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = spacing.lSmall),
            color = Color.LightGray
        )
        LabelWithIcon(
            heading = "Check list",
            content = "Add item...",
            icon = Icons.Outlined.Checklist
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        ConfirmCancelButton(
            confirmLabel = "Edit",
            onConfirm = {},
            cancelLabel = "Close",
            onCancel = closeBottomSheet
        )
    }
}

@Composable
fun TaskStatusArea() {
    Row(
        modifier = Modifier.padding(spacing.small)
    ) {
        TaskStatusItem(
            modifier = Modifier.weight(1f),
            status = TaskStatus.PENDING,
            isCurrentStatus = true
        )
        TaskStatusItem(
            modifier = Modifier.weight(1f),
            status = TaskStatus.DONE,
            isCurrentStatus = false
        )
        TaskStatusItem(
            modifier = Modifier.weight(1f),
            status = TaskStatus.CANCELED,
            isCurrentStatus = false
        )
    }
}

@Composable
fun TaskStatusItem(
    modifier: Modifier = Modifier,
    status: TaskStatus,
    isCurrentStatus: Boolean
) {
    val color = if (isCurrentStatus) {
        when (status) {
            TaskStatus.PENDING -> MaterialTheme.colorScheme.primary
            TaskStatus.DONE -> Color.Green
            TaskStatus.CANCELED -> Color.Red
        }
    } else Color.LightGray

    val icon = when (status) {
        TaskStatus.PENDING -> Icons.Default.Remove
        TaskStatus.DONE -> Icons.Default.Check
        TaskStatus.CANCELED -> Icons.Outlined.Close
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        CircularAvatar(
            backgroundColor = color
        ) {
            Icon(
                imageVector = icon,
                modifier = Modifier.size(24.dp),
                contentDescription = status.name,
                tint = color
            )
        }
        Spacer(modifier = Modifier.height(spacing.xSmall))
        Box(
            Modifier
                .background(
                    color = if (isCurrentStatus) color.copy(alpha = .05f) else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(
                    horizontal = spacing.lSmall,
                    vertical = spacing.xSmall,
                ),
        ) {
            Text(text = status.name.titleCase(), fontSize = textSize.lSmall)
        }
    }
}

@Composable
fun LabelWithIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    heading: String? = null,
    content: String,
) {
    Row(
        modifier = modifier.padding(
            start = spacing.medium,
            end = spacing.medium,
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "mark task as done",
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(spacing.medium))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (heading != null) Text(
                text = heading,
                fontSize = textSize.lSmall
            )
            Text(text = content)
        }
    }
}

@Preview
@Composable
fun TaskDetailsPreview() {
    Surface {
        TaskDetails {}
    }
}