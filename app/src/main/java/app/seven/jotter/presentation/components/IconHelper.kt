package app.seven.jotter.presentation.components

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import app.seven.jotter.presentation.theme.spacing

@Composable
fun CircularIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentDescription: String
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = backgroundColor.copy(alpha = 0.1f))
            .padding(spacing().xSmall)
    ) {
        Icon(imageVector, contentDescription, tint = backgroundColor)
    }
}

@Composable
fun CircularAvatar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable () -> Unit
) {
    val bColor = if (backgroundColor == Color.Transparent) {
        Color.Transparent
    } else {
        backgroundColor.copy(alpha = 0.1f)
    }

    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(
                color = bColor
            )
            .padding(spacing().xSmall)
    ) {
        content()
    }
}

@Preview
@Composable
fun CircularIconPreview() {
    CircularIcon(imageVector = Icons.Default.AlarmOff, contentDescription = "")
}

@Preview
@Composable
fun CircularAvatarPreview() {
    CircularAvatar {
        Text(text = "0")
    }
}
