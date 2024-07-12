package app.seven.jotter.core.usecases

import app.seven.jotter.core.models.TaskModel
import app.seven.jotter.core.models.UseCase
import javax.inject.Inject

class UpsertTaskUseCase @Inject constructor() : UseCase<TaskModel, Unit> {
    override suspend fun <INPUT> execute(param: INPUT) {

    }
}