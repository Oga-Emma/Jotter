package app.seven.jotter.di

import app.seven.jotter.core.mappers.TaskEntityMapper
import app.seven.jotter.core.mappers.TaskReminderEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ComponentModule {

    @Singleton
    @Provides
    fun provideTaskEntityMapper(): TaskEntityMapper {
        return TaskEntityMapper()
    }

    @Singleton
    @Provides
    fun provideTTaskReminderEntityMapper(): TaskReminderEntityMapper {
        return TaskReminderEntityMapper()
    }
}
