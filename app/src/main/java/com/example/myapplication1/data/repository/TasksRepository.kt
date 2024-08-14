package com.example.myapplication1.data.repository

import com.example.myapplication1.data.datasource.TasksLocalDataSource
import com.example.myapplication1.data.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TasksRepository @Inject constructor(private val dataSource: TasksLocalDataSource) {
    /** Получение текущего состояния списка задач */
    fun getTasks(): Flow<List<Task>> {
        return dataSource.getTasks()
    }

    /** Добавить задачу */
    fun addTask(task: Task) {
        dataSource.addTask(task)
    }

    fun deleteTasks(tasksToDelete: Set<UUID>) {
        dataSource.deleteTasks(tasksToDelete)
    }
//
//    /** Удалить задачу */
//    fun delete(taskUUID: UUID) {
//        tasks.remove(taskUUID)
//    }
}