package app.seven.jotter.core.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import app.seven.jotter.core.data.local.entities.TaskEntity

@Dao
abstract class TaskDao : BaseDao<TaskEntity> {
//    @Query("SELECT * FROM tasks")
//    fun getAll(): List<TaskEntity>
//
//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<TaskEntity>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): TaskEntity
//
//    @Insert
//    fun insertAll(vararg users: TaskEntity)
//
//    @Delete
//    fun delete(user: TaskEntity)
}