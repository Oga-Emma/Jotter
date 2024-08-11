package app.seven.jotter.core.repositories

import androidx.room.Transaction
import app.seven.jotter.core.data.local.daos.TaskDao
import app.seven.jotter.core.data.local.daos.TaskReminderDao
import app.seven.jotter.core.data.local.entities.TaskEntity
import app.seven.jotter.core.data.local.entities.TaskReminderEntity
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val taskReminderDao: TaskReminderDao,
) {

    @Transaction
    fun upsert(entity: TaskEntity, reminders: List<TaskReminderEntity>) {
        saveTask(entity)
        saveReminders(reminders)
    }

    private fun saveReminders(reminders: List<TaskReminderEntity>) {
        taskReminderDao.insert(*reminders.toTypedArray())
    }

    private fun saveTask(entity: TaskEntity) {
        taskDao.insert(entity)
    }

}