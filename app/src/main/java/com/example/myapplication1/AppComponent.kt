package com.example.myapplication1

import android.content.Context
import androidx.room.Room
import com.example.myapplication1.data.datasource.TasksLocalDataSource
import com.example.myapplication1.data.repository.TasksRepository
import com.example.myapplication1.ui.stateholders.TasksViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun tasksRepository(): TasksRepository
    fun inject(activity: MainActivity)
}

@Module(includes = [DatabaseModule::class])
object AppModule

@Module
object DatabaseModule {

//    @Singleton
//    @Provides
//    fun provideDatabase(@ApplicationContext context : Context) {
//        val db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, "database-name"
//        ).build()
//    }
}