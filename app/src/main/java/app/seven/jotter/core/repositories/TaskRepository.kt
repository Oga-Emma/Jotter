package app.seven.jotter.core.repositories

import androidx.room.Transaction
import app.seven.jotter.core.data.local.daos.TaskDao
import app.seven.jotter.core.data.local.daos.TaskReminderDao
import app.seven.jotter.core.data.local.entities.TaskEntity
import app.seven.jotter.core.data.local.entities.TaskReminderEntity
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val taskReminderDao: TaskReminderDao
) {

    @Transaction
    suspend fun upsert(entity: TaskEntity, reminders: List<TaskReminderEntity>) {
        saveTask(entity)
        saveReminders(reminders)
    }

    private suspend fun saveReminders(reminders: List<TaskReminderEntity>) {
        if(reminders.isEmpty()) return
        taskReminderDao.insert(*reminders.toTypedArray())
    }

    private suspend fun saveTask(entity: TaskEntity) {
        taskDao.insert(entity)
    }

}