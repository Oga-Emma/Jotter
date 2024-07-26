package app.seven.jotter.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.seven.jotter.core.models.TaskCategory
import app.seven.jotter.core.models.TaskCheckListItem
import app.seven.jotter.core.models.TaskFrequency
import app.seven.jotter.core.models.TaskPriority
import java.time.LocalDate
import java.util.UUID

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: UUID,
    val description: String,
    val note: String,
    val category: TaskCategory,
    val date: LocalDate,
    val priority: TaskPriority,
    val checkList: List<TaskCheckListItem>,
    val frequency: TaskFrequency
)

