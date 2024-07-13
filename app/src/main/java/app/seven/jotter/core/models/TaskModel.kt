package app.seven.jotter.core.models

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class TaskModel(
    val id: UUID,
    val description: String,
    val note: String,
    val category: TaskCategory,
    val date: LocalDate,
    val reminders: List<TaskReminder>,
    val checkList: List<TaskCheckListItem>,
    val priority: TaskPriority,
    val frequency: TaskFrequency
)

data class TaskCheckListItem(
    val id: UUID,
    val description: String,
    val isDone: Boolean
)

object TaskModelCreator {
    fun newTaskWithDefaultValues() = TaskModel(
        id = UUID.randomUUID(),
        description = "",
        note = "",
        category = TaskCategory.TASK,
        date = LocalDate.now(),
        reminders = mutableListOf(),
        checkList = mutableListOf(),
        priority = TaskPriority.MEDIUM,
        frequency = TaskFrequency.ONCE
    )
}

enum class TaskCategory {
    TASK,
    WORK,
    HEALTH,
    FOOD,
    SPORT
}

data class TaskReminder(
    val id: UUID,
    val type: TaskReminderType,
    val time: LocalTime
)

enum class TaskReminderType {
    NONE,
    ALARM,
    NOTIFICATION,
}

enum class TaskPriority {
    LOW,
    MEDIUM,
    HIGH
}

enum class TaskFrequency {
    ONCE,
    DAILY,
    WEEKLY,
    WEEKDAYS,
    WEEKENDS,
}
