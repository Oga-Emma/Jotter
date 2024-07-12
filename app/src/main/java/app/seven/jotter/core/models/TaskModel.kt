package app.seven.jotter.core.models

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

data class TaskModel(
    val id: UUID,
    val description: String,
    val category: TaskCategory,
    val date: LocalDate,
    val reminders: List<TaskReminder>,
    val list: List<String>,
    val priority: TaskPriority,
    val frequency: TaskFrequency
)

object TaskModelCreator {
    fun newTaskWithDefaultValues() = TaskModel(
        id = UUID.randomUUID(),
        description = "",
        category = TaskCategory.TASK,
        date = LocalDate.now(),
        reminders = mutableListOf(),
        list = mutableListOf(),
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
