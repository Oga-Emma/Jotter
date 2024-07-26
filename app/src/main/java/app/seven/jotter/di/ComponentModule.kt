package app.seven.jotter.di

import android.content.Context
import androidx.room.Room
import app.seven.jotter.common.Constants
import app.seven.jotter.core.data.local.daos.TaskDao
import app.seven.jotter.core.data.local.daos.TaskReminderDao
import app.seven.jotter.core.database.AppDatabase
import app.seven.jotter.core.mappers.TaskEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
}
