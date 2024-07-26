package app.seven.jotter.core.mappers

import app.seven.jotter.core.data.local.entities.TaskEntity
import app.seven.jotter.core.models.TaskModel

class TaskEntityMapper {
    fun from(taskModel: TaskModel): TaskEntity {
        return TaskEntity(
            id = taskModel.id,
            description = taskModel.description,
            note = taskModel.note,
            category = taskModel.category,
            date = taskModel.date,
            checkList = taskModel.checkList,
            priority = taskModel.priority,
            frequency = taskModel.frequency,
        )
    }
}