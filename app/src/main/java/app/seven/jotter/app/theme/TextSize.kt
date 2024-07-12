package app.seven.jotter.app.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class TextSize (
    val default: TextUnit = 0.sp,
    val xSmall: TextUnit = 4.sp,
    val small: TextUnit = 8.sp,
    val lSmall: TextUnit = 12.sp,
    val xMedium: TextUnit = 14.sp,
    val medium: TextUnit = 16.sp,
    val lMedium: TextUnit = 20.sp,
    val xLarge: TextUnit = 24.sp,
    val large: TextUnit = 32.sp,
    val lLarge: TextUnit = 64.sp
)

val LocalTextSize = compositionLocalOf { TextSize() }

val MaterialTheme.textSize: TextSize
    @Composable
    @ReadOnlyComposable
    get() = LocalTextSize.current
