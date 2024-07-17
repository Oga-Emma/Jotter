package app.seven.jotter.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.seven.jotter.core.models.TaskCategory
import app.seven.jotter.core.models.TaskFrequency
import app.seven.jotter.core.models.TaskPriority
import app.seven.jotter.core.models.TaskReminderType
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: UUID,
    val description: String,
    val note: String,
    val category: TaskCategory,
    val date: LocalDate,
    val reminders: List<TaskReminderEntity>,
    val checkList: List<TaskCheckListItemEntity>,
    val priority: TaskPriority,
    val frequency: TaskFrequency
)

//@Entity(tableName = "task_checklist_items"
//        foreignKeys = [
//    ForeignKey(
//        entity = Customer::class,
//        parentColumns = arrayOf("customer_id"),
//        childColumns = arrayOf("customer_id"),
//        onUpdate = ForeignKey.CASCADE,
//        onDelete = ForeignKey.CASCADE
//    )]
//)
data class TaskCheckListItemEntity(
    @PrimaryKey
    val id: UUID,
    val description: String,
    val isDone: Boolean
)

@Entity(tableName = "task_reminders")
data class TaskReminderEntity(
    @PrimaryKey
    val id: UUID,
    val type: TaskReminderType,
    val time: LocalTime
)
