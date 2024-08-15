package com.example.myapplication1

import android.content.Context
import androidx.room.Room
import com.example.myapplication1.data.db.AppDatabase
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
}

@Module(includes = [DatabaseModule::class])
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "my-database"
        ).build()
    }
}

@Module
object DatabaseModule {
}