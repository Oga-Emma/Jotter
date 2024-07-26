package app.seven.jotter.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing (
    val default: Dp = 0.dp,
    val xSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val lSmall: Dp = 12.dp,
    val xMedium: Dp = 14.dp,
    val medium: Dp = 16.dp,
    val lMedium: Dp = 24.dp,
    val xLarge: Dp = 28.dp,
    val large: Dp = 32.dp,
    val lLarge: Dp = 64.dp
)

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
