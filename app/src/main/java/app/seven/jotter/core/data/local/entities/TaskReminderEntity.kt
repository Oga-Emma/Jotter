package app.seven.jotter.core.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.seven.jotter.core.models.TaskReminderType
import java.time.LocalTime
import java.util.UUID

@Entity(tableName = "task_reminders")
data class TaskReminderEntity(
    @PrimaryKey
    val id: UUID,

    @ColumnInfo(name = "task_id")
    val taskId: UUID,

    @ColumnInfo(name = "task_reminder_type")
    val type: TaskReminderType,
    val time: LocalTime
)