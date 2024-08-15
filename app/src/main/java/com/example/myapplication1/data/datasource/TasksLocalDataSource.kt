package com.example.myapplication1.data.datasource

import com.example.myapplication1.data.db.AppDatabase
import com.example.myapplication1.data.db.entities.TaskEntity
import com.example.myapplication1.data.model.Task
import java.util.UUID
import javax.inject.Inject

class TasksLocalDataSource @Inject constructor(private val database: AppDatabase) {

    fun getTasks(): List<Task> {
        return database
            .taskDao()
            .getAll()
            .map { Task(UUID.fromString(it.uuid), it.text) }
    }


    fun addTask(task: Task) {
        database
            .taskDao()
            .insertAll(
                TaskEntity(task.uuid.toString(), task.text)
            )
    }

    fun deleteTasks(tasksToDelete: Set<UUID>) {
        database
            .taskDao()
            .deleteAll(
                tasksToDelete.map { it.toString() }
            )
    }
}