package app.seven.jotter.core.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import app.seven.jotter.core.data.local.entities.TaskEntity
import app.seven.jotter.core.data.local.entities.TaskReminderEntity

@Dao
abstract class TaskReminderDao : BaseDao<TaskReminderEntity> {
}