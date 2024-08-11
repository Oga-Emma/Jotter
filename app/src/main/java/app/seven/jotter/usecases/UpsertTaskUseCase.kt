package app.seven.jotter.usecases

import app.seven.jotter.core.mappers.TaskEntityMapper
import app.seven.jotter.core.mappers.TaskReminderEntityMapper
import app.seven.jotter.core.models.SuspendUseCase
import app.seven.jotter.core.models.TaskModel
import app.seven.jotter.core.repositories.TaskRepository
import javax.inject.Inject

class UpsertTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskEntityMapper: TaskEntityMapper,
    private val taskReminderEntityMapper: TaskReminderEntityMapper,
    ) : SuspendUseCase<TaskModel, Unit> {

    override suspend fun execute(param: TaskModel){

        val entity = taskEntityMapper.from(param)
        val reminders = param.reminders.map {
            taskReminderEntityMapper.from(it, entity.id)
        }.toList()

        taskRepository.upsert(entity, reminders)
    }
}