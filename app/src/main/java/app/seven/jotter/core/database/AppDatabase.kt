package app.seven.jotter.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.seven.jotter.core.data.local.daos.TaskDao
import app.seven.jotter.core.data.local.daos.TaskReminderDao
import app.seven.jotter.core.data.local.entities.TaskEntity
import app.seven.jotter.core.data.local.entities.TaskReminderEntity
import app.seven.jotter.core.utils.RoomTypeConverters

@Database(
    entities = [
        TaskEntity::class,
        TaskReminderEntity::class,
    ], version = 1
)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun taskReminderDao(): TaskReminderDao
}