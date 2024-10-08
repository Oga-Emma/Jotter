package app.seven.jotter.di

import android.content.Context
import androidx.room.Room
import app.seven.jotter.common.Constants
import app.seven.jotter.core.data.local.daos.TaskDao
import app.seven.jotter.core.data.local.daos.TaskReminderDao
import app.seven.jotter.core.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDB(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideTaskDao(appDatabase: AppDatabase): TaskDao {
        return appDatabase.taskDao()
    }

    @Singleton
    @Provides
    fun provideTaskReminderEntityDao(appDatabase: AppDatabase): TaskReminderDao {
        return appDatabase.taskReminderDao()
    }
}
