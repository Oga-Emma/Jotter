package app.seven.jotter.presentation.screens.taskeditors.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.WorkHistory
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import app.seven.jotter.core.models.TaskCategory

object IconHelper {
    private fun getIcon(category: TaskCategory): ImageVector =
        when (category) {
            TaskCategory.TASK -> Icons.Default.AddTask
            TaskCategory.WORK -> Icons.Default.WorkHistory
            TaskCategory.HEALTH -> Icons.Default.HealthAndSafety
            TaskCategory.FOOD -> Icons.Default.Fastfood
            TaskCategory.SPORT -> Icons.Default.SportsBasketball
        }

    @Composable
    fun GetCategoryIcon(category: TaskCategory) {
        Icon(
            imageVector = getIcon(category),
            contentDescription = "Localized description",
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}