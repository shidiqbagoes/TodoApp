package android.paninti.todoapp.data.di

import android.content.Context
import android.paninti.todoapp.data.local.TodoDatabase
import android.paninti.todoapp.util.Const
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
            Room.databaseBuilder(
                context,
                TodoDatabase::class.java,
                Const.Database.databaseName).build()

    @Singleton
    @Provides
    fun provideTodoDao(database: TodoDatabase) =
            database.todoDao()
}