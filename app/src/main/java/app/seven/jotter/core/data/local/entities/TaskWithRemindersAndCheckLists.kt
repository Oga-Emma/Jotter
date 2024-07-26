package app.seven.jotter.core.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithRemindersAndCheckLists(
    @Embedded val task: TaskEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "taskId"
    )
    val reminders: List<TaskReminderEntity>,
)