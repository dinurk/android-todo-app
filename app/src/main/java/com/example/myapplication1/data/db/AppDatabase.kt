package com.example.myapplication1.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication1.data.db.dao.TaskDao
import com.example.myapplication1.data.db.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}