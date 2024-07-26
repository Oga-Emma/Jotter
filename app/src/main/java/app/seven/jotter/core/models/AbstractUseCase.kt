package app.seven.jotter.core.models

interface UseCase <INPUT, OUTPUT> {
    fun execute(param: INPUT): OUTPUT
}

interface SuspendUseCase <INPUT, OUTPUT> {
    suspend fun execute(param: INPUT): OUTPUT
}
