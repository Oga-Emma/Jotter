package app.seven.jotter.core.mappers

import app.seven.jotter.core.data.local.entities.TaskEntity
import app.seven.jotter.core.data.local.entities.TaskReminderEntity
import app.seven.jotter.core.models.TaskModel
import app.seven.jotter.core.models.TaskReminder
import java.util.UUID

class TaskReminderEntityMapper {
    fun from(reminder: TaskReminder, taskId: UUID): TaskReminderEntity {
        return TaskReminderEntity(
            id = reminder.id,
            taskId = taskId,
            type = reminder.type,
            time = reminder.time,
        )
    }
}