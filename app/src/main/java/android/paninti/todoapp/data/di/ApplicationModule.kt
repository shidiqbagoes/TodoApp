package android.paninti.todoapp.data.di

import android.app.Application
import android.content.Context
import android.paninti.todoapp.data.source.TodoLocalDataSource
import android.paninti.todoapp.data.local.dao.TodoDao
import android.paninti.todoapp.data.preferences.AccessManager
import android.paninti.todoapp.data.repositories.LoginRepository
import android.paninti.todoapp.data.source.LoginDataSource
import android.paninti.todoapp.data.source.TodoRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module(includes = [DatabaseModule::class])
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Provides
    fun provideApplication(application: Application): Context = application

    @Provides
    fun provideAccessManager(context: Context) = AccessManager(context)

    @Provides
    fun provideTodoLocalDataSource(todoDao: TodoDao) = TodoLocalDataSource(todoDao)

    @Provides
    fun provideTodoRemoteDataSource() = TodoRemoteDataSource()

    @Provides
    fun provideLoginDataSource() = LoginDataSource()

    @Provides
    fun provideLoginRepository(dataSource: LoginDataSource, accessManager: AccessManager) =
            LoginRepository(dataSource, accessManager)

}