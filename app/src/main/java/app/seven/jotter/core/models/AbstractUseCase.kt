package app.seven.jotter.core.models

interface UseCase <INPUT, OUTPUT> {
    suspend fun<INPUT> execute(param: INPUT): OUTPUT
}