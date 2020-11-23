package android.paninti.todoapp.service.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Provides
    fun provideApplication(application: Application): Context = application
}